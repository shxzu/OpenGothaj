package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPacket1_13$6
extends PacketHandlers {
    PlayerPacket1_13$6() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            int size;
            int newSize = size = wrapper.get(Type.VAR_INT, 0).intValue();
            block4: for (int i = 0; i < size; ++i) {
                int categoryId = wrapper.read(Type.VAR_INT);
                int statisticId = wrapper.read(Type.VAR_INT);
                String name = "";
                switch (categoryId) {
                    case 0: 
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: 
                    case 5: 
                    case 6: 
                    case 7: {
                        wrapper.read(Type.VAR_INT);
                        --newSize;
                        continue block4;
                    }
                    case 8: {
                        name = (String)((Protocol1_12_2To1_13)PlayerPacket1_13.this.protocol).getMappingData().getStatisticMappings().get(statisticId);
                        if (name == null) {
                            wrapper.read(Type.VAR_INT);
                            --newSize;
                            continue block4;
                        }
                    }
                    default: {
                        wrapper.write(Type.STRING, name);
                        wrapper.passthrough(Type.VAR_INT);
                    }
                }
            }
            if (newSize != size) {
                wrapper.set(Type.VAR_INT, 0, newSize);
            }
        });
    }
}
