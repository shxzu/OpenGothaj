package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.api.rewriter.item.ReplacementItemRewriter;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class WorldPackets$1
extends PacketHandlers {
    final Protocol1_7_6_10To1_8 val$protocol;

    WorldPackets$1(Protocol1_7_6_10To1_8 protocol1_7_6_10To1_8) {
        this.val$protocol = protocol1_7_6_10To1_8;
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.INT);
        this.handler(wrapper -> {
            BlockChangeRecord[] records = wrapper.read(Type.BLOCK_CHANGE_RECORD_ARRAY);
            wrapper.write(Type.SHORT, (short)records.length);
            wrapper.write(Type.INT, records.length * 4);
            for (BlockChangeRecord record : records) {
                wrapper.write(Type.SHORT, (short)(record.getSectionX() << 12 | record.getSectionZ() << 8 | record.getY()));
                wrapper.write(Type.SHORT, (short)((ReplacementItemRewriter)this.val$protocol.getItemRewriter()).replace(record.getBlockId()));
            }
        });
    }
}
