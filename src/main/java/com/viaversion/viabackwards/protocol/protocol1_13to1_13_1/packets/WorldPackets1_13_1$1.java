package com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.packets;

import com.viaversion.viabackwards.protocol.protocol1_13to1_13_1.Protocol1_13To1_13_1;
import com.viaversion.viaversion.api.minecraft.BlockFace;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class WorldPackets1_13_1$1
extends PacketHandlers {
    final Protocol1_13To1_13_1 val$protocol;

    WorldPackets1_13_1$1(Protocol1_13To1_13_1 protocol1_13To1_13_1) {
        this.val$protocol = protocol1_13To1_13_1;
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.POSITION1_8);
        this.map(Type.INT);
        this.handler(wrapper -> {
            int id = wrapper.get(Type.INT, 0);
            int data = wrapper.get(Type.INT, 1);
            if (id == 1010) {
                wrapper.set(Type.INT, 1, this.val$protocol.getMappingData().getNewItemId(data));
            } else if (id == 2001) {
                wrapper.set(Type.INT, 1, this.val$protocol.getMappingData().getNewBlockStateId(data));
            } else if (id == 2000) {
                switch (data) {
                    case 0: 
                    case 1: {
                        Position pos = wrapper.get(Type.POSITION1_8, 0);
                        BlockFace relative = data == 0 ? BlockFace.BOTTOM : BlockFace.TOP;
                        wrapper.set(Type.POSITION1_8, 0, pos.getRelative(relative));
                        wrapper.set(Type.INT, 1, 4);
                        break;
                    }
                    case 2: {
                        wrapper.set(Type.INT, 1, 1);
                        break;
                    }
                    case 3: {
                        wrapper.set(Type.INT, 1, 7);
                        break;
                    }
                    case 4: {
                        wrapper.set(Type.INT, 1, 3);
                        break;
                    }
                    case 5: {
                        wrapper.set(Type.INT, 1, 5);
                    }
                }
            }
        });
    }
}
