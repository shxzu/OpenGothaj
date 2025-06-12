package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15;

import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14_4to1_14_3.ClientboundPackets1_14_4;

class Protocol1_14_4To1_15$1
extends PacketHandlers {
    Protocol1_14_4To1_15$1() {
    }

    @Override
    public void register() {
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.handler(wrapper -> {
            PacketWrapper soundPacket = wrapper.create(ClientboundPackets1_14_4.SOUND);
            soundPacket.write(Type.VAR_INT, 243);
            soundPacket.write(Type.VAR_INT, 4);
            soundPacket.write(Type.INT, this.toEffectCoordinate(wrapper.get(Type.FLOAT, 0).floatValue()));
            soundPacket.write(Type.INT, this.toEffectCoordinate(wrapper.get(Type.FLOAT, 1).floatValue()));
            soundPacket.write(Type.INT, this.toEffectCoordinate(wrapper.get(Type.FLOAT, 2).floatValue()));
            soundPacket.write(Type.FLOAT, Float.valueOf(4.0f));
            soundPacket.write(Type.FLOAT, Float.valueOf(1.0f));
            soundPacket.send(Protocol1_14_4To1_15.class);
        });
    }

    private int toEffectCoordinate(float coordinate) {
        return (int)(coordinate * 8.0f);
    }
}
