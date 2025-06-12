package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPacket1_13$1
extends PacketHandlers {
    PlayerPacket1_13$1() {
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            packetWrapper.cancel();
            packetWrapper.create(2, new PacketHandler(){

                @Override
                public void handle(PacketWrapper newWrapper) throws Exception {
                    newWrapper.write(Type.VAR_INT, packetWrapper.read(Type.VAR_INT));
                    newWrapper.write(Type.BOOLEAN, false);
                }
            }).sendToServer(Protocol1_12_2To1_13.class);
        });
    }
}
