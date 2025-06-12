package com.viaversion.viabackwards.protocol.protocol1_19_3to1_19_4;

import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;
import com.viaversion.viaversion.rewriter.CommandRewriter;

class Protocol1_19_3To1_19_4$1
extends CommandRewriter<ClientboundPackets1_19_4> {
    Protocol1_19_3To1_19_4$1(Protocol arg0) {
        super(arg0);
    }

    @Override
    public void handleArgument(PacketWrapper wrapper, String argumentType) throws Exception {
        switch (argumentType) {
            case "minecraft:heightmap": {
                wrapper.write(Type.VAR_INT, 0);
                break;
            }
            case "minecraft:time": {
                wrapper.read(Type.INT);
                break;
            }
            case "minecraft:resource": 
            case "minecraft:resource_or_tag": {
                String resource = wrapper.read(Type.STRING);
                wrapper.write(Type.STRING, resource.equals("minecraft:damage_type") ? "minecraft:mob_effect" : resource);
                break;
            }
            default: {
                super.handleArgument(wrapper, argumentType);
            }
        }
    }
}
