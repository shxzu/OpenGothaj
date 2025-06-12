package com.viaversion.viabackwards.protocol.protocol1_18_2to1_19;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.signature.SignableCommandArgumentsProvider;
import com.viaversion.viaversion.api.minecraft.signature.model.DecoratableMessage;
import com.viaversion.viaversion.api.minecraft.signature.model.MessageMetadata;
import com.viaversion.viaversion.api.minecraft.signature.storage.ChatSession1_19_0;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_19to1_18_2.ServerboundPackets1_19;
import com.viaversion.viaversion.util.Pair;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

class Protocol1_18_2To1_19$6
extends PacketHandlers {
    Protocol1_18_2To1_19$6() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.handler(wrapper -> {
            ChatSession1_19_0 chatSession = wrapper.user().get(ChatSession1_19_0.class);
            UUID sender = wrapper.user().getProtocolInfo().getUuid();
            Instant timestamp = Instant.now();
            long salt = ThreadLocalRandom.current().nextLong();
            wrapper.write(Type.LONG, timestamp.toEpochMilli());
            wrapper.write(Type.LONG, chatSession != null ? salt : 0L);
            String message = wrapper.get(Type.STRING, 0);
            if (!message.isEmpty() && message.charAt(0) == '/') {
                String command = message.substring(1);
                wrapper.setPacketType(ServerboundPackets1_19.CHAT_COMMAND);
                wrapper.set(Type.STRING, 0, command);
                SignableCommandArgumentsProvider argumentsProvider = Via.getManager().getProviders().get(SignableCommandArgumentsProvider.class);
                if (chatSession != null && argumentsProvider != null) {
                    MessageMetadata metadata = new MessageMetadata(sender, timestamp, salt);
                    List<Pair<String, String>> arguments = argumentsProvider.getSignableArguments(command);
                    wrapper.write(Type.VAR_INT, arguments.size());
                    for (Pair<String, String> argument : arguments) {
                        byte[] signature = chatSession.signChatMessage(metadata, new DecoratableMessage(argument.value()));
                        wrapper.write(Type.STRING, argument.key());
                        wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, signature);
                    }
                } else {
                    wrapper.write(Type.VAR_INT, 0);
                }
            } else if (chatSession != null) {
                MessageMetadata metadata = new MessageMetadata(sender, timestamp, salt);
                DecoratableMessage decoratableMessage = new DecoratableMessage(message);
                byte[] signature = chatSession.signChatMessage(metadata, decoratableMessage);
                wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, signature);
            } else {
                wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, EMPTY_BYTES);
            }
            wrapper.write(Type.BOOLEAN, false);
        });
    }
}
