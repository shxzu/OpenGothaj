package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class WorldPackets$9
extends PacketHandlers {
    WorldPackets$9() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_8, Types1_7_6_10.SHORT_POSITION);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.NAMED_COMPOUND_TAG, Types1_7_6_10.COMPRESSED_NBT);
    }
}
