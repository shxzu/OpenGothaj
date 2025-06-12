package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.api.rewriter.item.ReplacementItemRewriter;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.sound.Effect;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class WorldPackets$8
extends PacketHandlers {
    final Protocol1_8To1_9 val$protocol;

    WorldPackets$8(Protocol1_8To1_9 protocol1_8To1_9) {
        this.val$protocol = protocol1_8To1_9;
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.POSITION1_8);
        this.map(Type.INT);
        this.map(Type.BOOLEAN);
        this.handler(packetWrapper -> {
            int id = packetWrapper.get(Type.INT, 0);
            if ((id = Effect.getOldId(id)) == -1) {
                packetWrapper.cancel();
                return;
            }
            packetWrapper.set(Type.INT, 0, id);
            if (id == 2001) {
                int replacedBlock = ((ReplacementItemRewriter)this.val$protocol.getItemRewriter()).replace(packetWrapper.get(Type.INT, 1));
                packetWrapper.set(Type.INT, 1, replacedBlock);
            }
        });
    }
}
