package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;

import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class BlockItemPackets1_15$1
extends PacketHandlers {
    BlockItemPackets1_15$1() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.BOOLEAN);
        this.map((Type)Type.DOUBLE, Type.FLOAT);
        this.map((Type)Type.DOUBLE, Type.FLOAT);
        this.map((Type)Type.DOUBLE, Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.INT);
        this.handler(wrapper -> {
            int id = wrapper.get(Type.INT, 0);
            if (id == 3 || id == 23) {
                int data = wrapper.passthrough(Type.VAR_INT);
                wrapper.set(Type.VAR_INT, 0, ((Protocol1_14_4To1_15)BlockItemPackets1_15.this.protocol).getMappingData().getNewBlockStateId(data));
            } else if (id == 32) {
                Item item = BlockItemPackets1_15.this.handleItemToClient(wrapper.read(Type.ITEM1_13_2));
                wrapper.write(Type.ITEM1_13_2, item);
            }
            int mappedId = ((Protocol1_14_4To1_15)BlockItemPackets1_15.this.protocol).getMappingData().getNewParticleId(id);
            if (id != mappedId) {
                wrapper.set(Type.INT, 0, mappedId);
            }
        });
    }
}
