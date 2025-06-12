package com.viaversion.viabackwards.protocol.protocol1_19to1_19_1;

import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage.ReceivedMessagesStorage;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.signature.SignableCommandArgumentsProvider;
import com.viaversion.viaversion.api.minecraft.signature.model.DecoratableMessage;
import com.viaversion.viaversion.api.minecraft.signature.model.MessageMetadata;
import com.viaversion.viaversion.api.minecraft.signature.storage.ChatSession1_19_1;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.Pair;
import java.util.List;
import java.util.UUID;

class Protocol1_19To1_19_1$3
extends PacketHandlers {
    Protocol1_19To1_19_1$3() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.LONG);
        this.map(Type.LONG);
        this.handler(wrapper -> {
            ReceivedMessagesStorage messagesStorage = wrapper.user().get(ReceivedMessagesStorage.class);
            ChatSession1_19_1 chatSession = wrapper.user().get(ChatSession1_19_1.class);
            SignableCommandArgumentsProvider argumentsProvider = Via.getManager().getProviders().get(SignableCommandArgumentsProvider.class);
            if (chatSession != null && argumentsProvider != null) {
                int signatures = wrapper.read(Type.VAR_INT);
                for (int i = 0; i < signatures; ++i) {
                    wrapper.read(Type.STRING);
                    wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                }
                UUID sender = wrapper.user().getProtocolInfo().getUuid();
                String command = wrapper.get(Type.STRING, 0);
                long timestamp = wrapper.get(Type.LONG, 0);
                long salt = wrapper.get(Type.LONG, 1);
                MessageMetadata metadata = new MessageMetadata(sender, timestamp, salt);
                List<Pair<String, String>> arguments = argumentsProvider.getSignableArguments(command);
                wrapper.write(Type.VAR_INT, arguments.size());
                for (Pair<String, String> argument : arguments) {
                    byte[] signature = chatSession.signChatMessage(metadata, new DecoratableMessage(argument.value()), messagesStorage.lastSignatures());
                    wrapper.write(Type.STRING, argument.key());
                    wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, signature);
                }
            } else {
                int signatures = wrapper.passthrough(Type.VAR_INT);
                for (int i = 0; i < signatures; ++i) {
                    wrapper.passthrough(Type.STRING);
                    wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
                    wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, EMPTY_BYTES);
                }
            }
            wrapper.passthrough(Type.BOOLEAN);
            messagesStorage.resetUnacknowledgedCount();
            wrapper.write(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
            wrapper.write(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
        });
    }
}
