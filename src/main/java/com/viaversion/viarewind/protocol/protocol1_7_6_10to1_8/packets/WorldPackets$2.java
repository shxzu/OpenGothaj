package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.api.rewriter.item.ReplacementItemRewriter;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class WorldPackets$2
extends PacketHandlers {
    final Protocol1_7_6_10To1_8 val$protocol;

    WorldPackets$2(Protocol1_7_6_10To1_8 protocol1_7_6_10To1_8) {
        this.val$protocol = protocol1_7_6_10To1_8;
    }

    @Override
    protected void register() {
        this.map(Type.POSITION1_8, Types1_7_6_10.U_BYTE_POSITION);
        this.handler(wrapper -> {
            int data = wrapper.read(Type.VAR_INT);
            data = ((ReplacementItemRewriter)this.val$protocol.getItemRewriter()).replace(data);
            wrapper.write(Type.VAR_INT, data >> 4);
            wrapper.write(Type.UNSIGNED_BYTE, (short)(data & 0xF));
        });
    }
}
