package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.BlockPlaceDestroyTracker;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$14
extends PacketHandlers {
    PlayerPackets$14() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_8);
        this.map((Type)Type.BYTE, Type.VAR_INT);
        this.read(Type.ITEM1_8);
        this.create(Type.VAR_INT, 0);
        this.map((Type)Type.BYTE, Type.UNSIGNED_BYTE);
        this.map((Type)Type.BYTE, Type.UNSIGNED_BYTE);
        this.map((Type)Type.BYTE, Type.UNSIGNED_BYTE);
        this.handler(packetWrapper -> {
            if (packetWrapper.get(Type.VAR_INT, 0) == -1) {
                packetWrapper.cancel();
                PacketWrapper useItem = PacketWrapper.create(29, null, packetWrapper.user());
                useItem.write(Type.VAR_INT, 0);
                PacketUtil.sendToServer(useItem, Protocol1_8To1_9.class, true, true);
            }
        });
        this.handler(packetWrapper -> {
            if (packetWrapper.get(Type.VAR_INT, 0) != -1) {
                packetWrapper.user().get(BlockPlaceDestroyTracker.class).place();
            }
        });
    }
}
