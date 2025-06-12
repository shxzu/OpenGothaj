package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3;

import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import com.viaversion.viaversion.api.minecraft.signature.model.MessageMetadata;
import com.viaversion.viaversion.api.minecraft.signature.storage.ChatSession1_19_3;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.BitSetType;
import java.util.BitSet;

class Protocol1_19_1To1_19_3$5
extends PacketHandlers {
    Protocol1_19_1To1_19_3$5() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.LONG);
        this.map(Type.LONG);
        this.read(Type.BYTE_ARRAY_PRIMITIVE);
        this.read(Type.BOOLEAN);
        this.read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
        this.read(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE);
        this.handler(wrapper -> {
            ChatSession1_19_3 chatSession = wrapper.user().get(ChatSession1_19_3.class);
            if (chatSession != null) {
                String message = wrapper.get(Type.STRING, 0);
                long timestamp = wrapper.get(Type.LONG, 0);
                long salt = wrapper.get(Type.LONG, 1);
                MessageMetadata metadata = new MessageMetadata(null, timestamp, salt);
                byte[] signature = chatSession.signChatMessage(metadata, message, new PlayerMessageSignature[0]);
                wrapper.write(OPTIONAL_SIGNATURE_BYTES_TYPE, signature);
            } else {
                wrapper.write(OPTIONAL_SIGNATURE_BYTES_TYPE, null);
            }
            wrapper.write(Type.VAR_INT, 0);
            wrapper.write(new BitSetType(20), new BitSet(20));
        });
    }
}
