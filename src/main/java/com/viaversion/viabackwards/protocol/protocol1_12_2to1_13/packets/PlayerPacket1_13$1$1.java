// ERROR: Unable to apply inner class name fixup
package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;

class PlayerPacket1_13.1
implements PacketHandler {
    final PacketWrapper val$packetWrapper;

    PlayerPacket1_13.1(PacketWrapper packetWrapper) {
        this.val$packetWrapper = packetWrapper;
    }

    @Override
    public void handle(PacketWrapper newWrapper) throws Exception {
        newWrapper.write(Type.VAR_INT, this.val$packetWrapper.read(Type.VAR_INT));
        newWrapper.write(Type.BOOLEAN, false);
    }
}
