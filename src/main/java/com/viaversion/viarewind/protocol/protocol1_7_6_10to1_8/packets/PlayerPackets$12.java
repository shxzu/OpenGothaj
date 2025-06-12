package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10.ClientboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.charset.StandardCharsets;

class PlayerPackets$12
extends PacketHandlers {
    final Protocol1_7_6_10To1_8 val$protocol;

    PlayerPackets$12(Protocol1_7_6_10To1_8 protocol1_7_6_10To1_8) {
        this.val$protocol = protocol1_7_6_10To1_8;
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.handler(packetWrapper -> {
            String channel = packetWrapper.get(Type.STRING, 0);
            if (channel.equalsIgnoreCase("MC|TrList")) {
                packetWrapper.passthrough(Type.INT);
                int size = packetWrapper.isReadable(Type.BYTE, 0) ? packetWrapper.passthrough(Type.BYTE).byteValue() : packetWrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                for (int i = 0; i < size; ++i) {
                    Item item = this.val$protocol.getItemRewriter().handleItemToClient(packetWrapper.read(Type.ITEM1_8));
                    packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, item);
                    item = this.val$protocol.getItemRewriter().handleItemToClient(packetWrapper.read(Type.ITEM1_8));
                    packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, item);
                    boolean has3Items = packetWrapper.passthrough(Type.BOOLEAN);
                    if (has3Items) {
                        item = this.val$protocol.getItemRewriter().handleItemToClient(packetWrapper.read(Type.ITEM1_8));
                        packetWrapper.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, item);
                    }
                    packetWrapper.passthrough(Type.BOOLEAN);
                    packetWrapper.read(Type.INT);
                    packetWrapper.read(Type.INT);
                }
            } else if (channel.equalsIgnoreCase("MC|Brand")) {
                packetWrapper.write(Type.REMAINING_BYTES, packetWrapper.read(Type.STRING).getBytes(StandardCharsets.UTF_8));
            }
            packetWrapper.cancel();
            packetWrapper.setPacketType(null);
            ByteBuf newPacketBuf = Unpooled.buffer();
            packetWrapper.writeToBuffer(newPacketBuf);
            PacketWrapper newWrapper = PacketWrapper.create(ClientboundPackets1_7_2_5.PLUGIN_MESSAGE, newPacketBuf, packetWrapper.user());
            newWrapper.passthrough(Type.STRING);
            if (newPacketBuf.readableBytes() <= Short.MAX_VALUE) {
                newWrapper.write(Type.SHORT, (short)newPacketBuf.readableBytes());
                newWrapper.send(Protocol1_7_6_10To1_8.class);
            }
        });
    }
}
