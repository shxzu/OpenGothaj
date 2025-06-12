package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets$11
extends PacketHandlers {
    final Protocol1_8To1_9 val$protocol;

    EntityPackets$11(Protocol1_8To1_9 protocol1_8To1_9) {
        this.val$protocol = protocol1_8To1_9;
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.handler(packetWrapper -> {
            int slot = packetWrapper.read(Type.VAR_INT);
            if (slot == 1) {
                packetWrapper.cancel();
            } else if (slot > 1) {
                --slot;
            }
            packetWrapper.write(Type.SHORT, (short)slot);
        });
        this.map(Type.ITEM1_8);
        this.handler(packetWrapper -> packetWrapper.set(Type.ITEM1_8, 0, this.val$protocol.getItemRewriter().handleItemToClient(packetWrapper.get(Type.ITEM1_8, 0))));
    }
}
