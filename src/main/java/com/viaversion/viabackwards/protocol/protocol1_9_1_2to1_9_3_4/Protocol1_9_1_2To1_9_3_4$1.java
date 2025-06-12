package com.viaversion.viabackwards.protocol.protocol1_9_1_2to1_9_3_4;

import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;

class Protocol1_9_1_2To1_9_3_4$1
extends PacketHandlers {
    Protocol1_9_1_2To1_9_3_4$1() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_8);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.NAMED_COMPOUND_TAG);
        this.handler(wrapper -> {
            if (wrapper.get(Type.UNSIGNED_BYTE, 0) == 9) {
                Position position = wrapper.get(Type.POSITION1_8, 0);
                CompoundTag tag = wrapper.get(Type.NAMED_COMPOUND_TAG, 0);
                wrapper.clearPacket();
                wrapper.setPacketType(ClientboundPackets1_9.UPDATE_SIGN);
                wrapper.write(Type.POSITION1_8, position);
                for (int i = 1; i < 5; ++i) {
                    Object textTag = tag.get("Text" + i);
                    String line = textTag instanceof StringTag ? ((StringTag)textTag).getValue() : "";
                    wrapper.write(Type.STRING, line);
                }
            }
        });
    }
}
