package com.viaversion.viabackwards.protocol.protocol1_19to1_19_1;

import com.viaversion.viabackwards.protocol.protocol1_19to1_19_1.storage.ReceivedMessagesStorage;
import com.viaversion.viaversion.api.minecraft.signature.model.DecoratableMessage;
import com.viaversion.viaversion.api.minecraft.signature.model.MessageMetadata;
import com.viaversion.viaversion.api.minecraft.signature.storage.ChatSession1_19_1;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.UUID;

class Protocol1_19To1_19_1$2
extends PacketHandlers {
    Protocol1_19To1_19_1$2() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.LONG);
        this.map(Type.LONG);
        this.read(Type.BYTE_ARRAY_PRIMITIVE);
        this.read(Type.BOOLEAN);
        this.handler(wrapper -> {
            ChatSession1_19_1 chatSession = wrapper.user().get(ChatSession1_19_1.class);
            ReceivedMessagesStorage messagesStorage = wrapper.user().get(ReceivedMessagesStorage.class);
            if (chatSession != null) {
                UUID sender = wrapper.user().getProtocolInfo().getUuid();
                String message = wrapper.get(Type.STRING, 0);
                long timestamp = wrapper.get(Type.LONG, 0);
                long salt = wrapper.get(Type.LONG, 1);
                MessageMetadata metadata = new MessageMetadata(sender, timestamp, salt);
                DecoratableMessage decoratableMessage = new DecoratableMessage(message);
                byte[] signature = chatSession.signChatMessage(metadata, decoratableMessage, messagesStorage.lastSignatures());
                wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, signature);
                wrapper.write(Type.BOOLEAN, decoratableMessage.isDecorated());
            } else {
                wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, EMPTY_BYTES);
                wrapper.write(Type.BOOLEAN, false);
            }
            messagesStorage.resetUnacknowledgedCount();
            wrapper.write(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY, messagesStorage.lastSignatures());
            wrapper.write(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE, null);
        });
    }
}
