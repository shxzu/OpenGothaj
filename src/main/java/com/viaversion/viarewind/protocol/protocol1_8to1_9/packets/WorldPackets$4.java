package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.api.rewriter.item.ReplacementItemRewriter;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class WorldPackets$4
extends PacketHandlers {
    final Protocol1_8To1_9 val$protocol;

    WorldPackets$4(Protocol1_8To1_9 protocol1_8To1_9) {
        this.val$protocol = protocol1_8To1_9;
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
        this.handler(packetWrapper -> {
            for (BlockChangeRecord record : packetWrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                int replacedCombined = ((ReplacementItemRewriter)this.val$protocol.getItemRewriter()).replace(record.getBlockId());
                record.setBlockId(replacedCombined);
            }
        });
    }
}
