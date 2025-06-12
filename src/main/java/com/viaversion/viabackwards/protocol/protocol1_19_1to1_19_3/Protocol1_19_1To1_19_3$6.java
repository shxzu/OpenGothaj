package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.PlayerMessageSignature;
import com.viaversion.viaversion.api.minecraft.signature.SignableCommandArgumentsProvider;
import com.viaversion.viaversion.api.minecraft.signature.model.MessageMetadata;
import com.viaversion.viaversion.api.minecraft.signature.storage.ChatSession1_19_3;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.BitSetType;
import com.viaversion.viaversion.util.Pair;
import java.util.BitSet;
import java.util.List;

class Protocol1_19_1To1_19_3$6
extends PacketHandlers {
    Protocol1_19_1To1_19_3$6() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.LONG);
        this.map(Type.LONG);
        this.handler(wrapper -> {
            ChatSession1_19_3 chatSession = wrapper.user().get(ChatSession1_19_3.class);
            SignableCommandArgumentsProvider argumentsProvider = Via.getManager().getProviders().get(SignableCommandArgumentsProvider.class);
            String command = wrapper.get(Type.STRING, 0);
            long timestamp = wrapper.get(Type.LONG, 0);
            long salt = wrapper.get(Type.LONG, 1);
            int signatures = wrapper.read(Type.VAR_INT);
            for (int i = 0; i < signatures; ++i) {
                wrapper.read(Type.STRING);
                wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
            }
            wrapper.read(Type.BOOLEAN);
            if (chatSession != null && argumentsProvider != null) {
                MessageMetadata metadata = new MessageMetadata(null, timestamp, salt);
                List<Pair<String, String>> arguments = argumentsProvider.getSignableArguments(command);
                wrapper.write(Type.VAR_INT, arguments.size());
                for (Pair<String, String> argument : arguments) {
                    byte[] signature = chatSession.signChatMessage(metadata, argument.value(), new PlayerMessageSignature[0]);
                    wrapper.write(Type.STRING, argument.key());
                    wrapper.write(SIGNATURE_BYTES_TYPE, signature);
                }
            } else {
                wrapper.write(Type.VAR_INT, 0);
            }
            boolean offset = false;
            BitSet acknowledged = new BitSet(20);
            wrapper.write(Type.VAR_INT, 0);
            wrapper.write(new BitSetType(20), acknowledged);
        });
        this.read(Type.PLAYER_MESSAGE_SIGNATURE_ARRAY);
        this.read(Type.OPTIONAL_PLAYER_MESSAGE_SIGNATURE);
    }
}
