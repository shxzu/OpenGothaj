package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$2
extends PacketHandlers {
    final Protocol1_8To1_9 val$protocol;

    PlayerPackets$2(Protocol1_8To1_9 protocol1_8To1_9) {
        this.val$protocol = protocol1_8To1_9;
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.handler(packetWrapper -> {
            String channel = packetWrapper.get(Type.STRING, 0);
            if (channel.equalsIgnoreCase("MC|TrList")) {
                packetWrapper.passthrough(Type.INT);
                int size = packetWrapper.isReadable(Type.BYTE, 0) ? packetWrapper.passthrough(Type.BYTE).byteValue() : packetWrapper.passthrough(Type.UNSIGNED_BYTE).shortValue();
                ItemRewriter itemRewriter = this.val$protocol.getItemRewriter();
                for (int i = 0; i < size; ++i) {
                    packetWrapper.write(Type.ITEM1_8, itemRewriter.handleItemToClient(packetWrapper.read(Type.ITEM1_8)));
                    packetWrapper.write(Type.ITEM1_8, itemRewriter.handleItemToClient(packetWrapper.read(Type.ITEM1_8)));
                    boolean has3Items = packetWrapper.passthrough(Type.BOOLEAN);
                    if (has3Items) {
                        packetWrapper.write(Type.ITEM1_8, itemRewriter.handleItemToClient(packetWrapper.read(Type.ITEM1_8)));
                    }
                    packetWrapper.passthrough(Type.BOOLEAN);
                    packetWrapper.passthrough(Type.INT);
                    packetWrapper.passthrough(Type.INT);
                }
            } else if (channel.equalsIgnoreCase("MC|BOpen")) {
                packetWrapper.read(Type.VAR_INT);
            }
        });
    }
}
