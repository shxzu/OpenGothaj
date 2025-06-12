package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$23
extends PacketHandlers {
    final Protocol1_7_6_10To1_8 val$protocol;

    PlayerPackets$23(Protocol1_7_6_10To1_8 protocol1_7_6_10To1_8) {
        this.val$protocol = protocol1_7_6_10To1_8;
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            int x = packetWrapper.read(Type.INT);
            short y = packetWrapper.read(Type.UNSIGNED_BYTE);
            int z = packetWrapper.read(Type.INT);
            packetWrapper.write(Type.POSITION1_8, new Position(x, (int)y, z));
            packetWrapper.passthrough(Type.BYTE);
            Item item = packetWrapper.read(Types1_7_6_10.COMPRESSED_NBT_ITEM);
            item = this.val$protocol.getItemRewriter().handleItemToServer(item);
            packetWrapper.write(Type.ITEM1_8, item);
            for (int i = 0; i < 3; ++i) {
                packetWrapper.passthrough(Type.BYTE);
            }
        });
    }
}
