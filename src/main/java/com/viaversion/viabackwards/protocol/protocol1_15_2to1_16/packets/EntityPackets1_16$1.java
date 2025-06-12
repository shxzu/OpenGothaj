package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_16$1
extends ValueTransformer<String, Integer> {
    EntityPackets1_16$1(Type arg0, Type arg1) {
        super(arg0, arg1);
    }

    @Override
    public Integer transform(PacketWrapper wrapper, String input) {
        switch (input) {
            case "minecraft:the_nether": {
                return -1;
            }
            default: {
                return 0;
            }
            case "minecraft:the_end": 
        }
        return 1;
    }
}
