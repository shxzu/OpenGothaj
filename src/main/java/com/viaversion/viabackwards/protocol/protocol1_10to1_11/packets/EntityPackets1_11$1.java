package com.viaversion.viabackwards.protocol.protocol1_10to1_11.packets;

import com.viaversion.viabackwards.protocol.protocol1_10to1_11.PotionSplashHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_11$1
extends PacketHandlers {
    EntityPackets1_11$1() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.POSITION1_8);
        this.map(Type.INT);
        this.handler(wrapper -> {
            int type = wrapper.get(Type.INT, 0);
            if (type == 2002 || type == 2007) {
                int mappedData;
                if (type == 2007) {
                    wrapper.set(Type.INT, 0, 2002);
                }
                if ((mappedData = PotionSplashHandler.getOldData(wrapper.get(Type.INT, 1))) != -1) {
                    wrapper.set(Type.INT, 1, mappedData);
                }
            }
        });
    }
}
