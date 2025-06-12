package com.viaversion.viabackwards.protocol.protocol1_20_2to1_20_3;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.rewriter.CommandRewriter1_19_4;
import com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.packet.ClientboundPackets1_20_3;

class Protocol1_20_2To1_20_3$1
extends CommandRewriter1_19_4<ClientboundPackets1_20_3> {
    Protocol1_20_2To1_20_3$1(Protocol arg0) {
        super(arg0);
    }

    @Override
    public void handleArgument(PacketWrapper wrapper, String argumentType) throws Exception {
        if (argumentType.equals("minecraft:style")) {
            wrapper.write(Type.VAR_INT, 1);
        } else {
            super.handleArgument(wrapper, argumentType);
        }
    }
}
