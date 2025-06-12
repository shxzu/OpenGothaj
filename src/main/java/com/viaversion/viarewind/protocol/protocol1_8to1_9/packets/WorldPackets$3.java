package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.api.rewriter.item.ReplacementItemRewriter;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class WorldPackets$3
extends PacketHandlers {
    final Protocol1_8To1_9 val$protocol;

    WorldPackets$3(Protocol1_8To1_9 protocol1_8To1_9) {
        this.val$protocol = protocol1_8To1_9;
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_8);
        this.map(Type.VAR_INT);
        this.handler(packetWrapper -> {
            int combined = packetWrapper.get(Type.VAR_INT, 0);
            int replacedCombined = ((ReplacementItemRewriter)this.val$protocol.getItemRewriter()).replace(combined);
            packetWrapper.set(Type.VAR_INT, 0, replacedCombined);
        });
    }
}
