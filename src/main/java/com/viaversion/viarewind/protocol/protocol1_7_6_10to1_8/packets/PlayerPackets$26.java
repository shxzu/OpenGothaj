package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$26
extends PacketHandlers {
    PlayerPackets$26() {
    }

    @Override
    public void register() {
        this.map(Type.FLOAT);
        this.map(Type.FLOAT);
        this.handler(packetWrapper -> {
            EntityTracker1_7_6_10 tracker;
            boolean jump = packetWrapper.read(Type.BOOLEAN);
            boolean unmount = packetWrapper.read(Type.BOOLEAN);
            short flags = 0;
            if (jump) {
                flags = (short)(flags + 1);
            }
            if (unmount) {
                flags = (short)(flags + 2);
            }
            packetWrapper.write(Type.UNSIGNED_BYTE, flags);
            if (unmount && tracker.spectatingPlayerId != (tracker = packetWrapper.user().get(EntityTracker1_7_6_10.class)).getPlayerId()) {
                PacketWrapper sneakPacket = PacketWrapper.create(11, null, packetWrapper.user());
                sneakPacket.write(Type.VAR_INT, tracker.getPlayerId());
                sneakPacket.write(Type.VAR_INT, 0);
                sneakPacket.write(Type.VAR_INT, 0);
                PacketWrapper unsneakPacket = PacketWrapper.create(11, null, packetWrapper.user());
                unsneakPacket.write(Type.VAR_INT, tracker.getPlayerId());
                unsneakPacket.write(Type.VAR_INT, 1);
                unsneakPacket.write(Type.VAR_INT, 0);
                PacketUtil.sendToServer(sneakPacket, Protocol1_7_6_10To1_8.class);
            }
        });
    }
}
