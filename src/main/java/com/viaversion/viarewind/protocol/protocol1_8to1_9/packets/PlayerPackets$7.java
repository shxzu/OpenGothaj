package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$7
extends PacketHandlers {
    PlayerPackets$7() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.handler(packetWrapper -> {
            String msg = packetWrapper.get(Type.STRING, 0);
            if (msg.toLowerCase().startsWith("/offhand")) {
                packetWrapper.cancel();
                PacketWrapper swapItems = PacketWrapper.create(19, null, packetWrapper.user());
                swapItems.write(Type.VAR_INT, 6);
                swapItems.write(Type.POSITION1_8, new Position(0, 0, 0));
                swapItems.write(Type.BYTE, (byte)-1);
                PacketUtil.sendToServer(swapItems, Protocol1_8To1_9.class, true, true);
            }
        });
    }
}
