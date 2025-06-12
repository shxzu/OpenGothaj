package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

class PlayerPackets$15
extends PacketHandlers {
    PlayerPackets$15() {
    }

    @Override
    public void register() {
        this.create(Type.STRING, "MC|RPack");
        this.handler(packetWrapper -> {
            ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
            try {
                Type.STRING.write(buf, packetWrapper.read(Type.STRING));
                packetWrapper.write(Type.SHORT_BYTE_ARRAY, (byte[])Type.REMAINING_BYTES.read(buf));
            }
            finally {
                buf.release();
            }
        });
        this.read(Type.STRING);
    }
}
