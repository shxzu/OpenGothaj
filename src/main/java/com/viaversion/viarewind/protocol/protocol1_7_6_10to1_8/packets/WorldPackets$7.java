package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viarewind.utils.ChatUtil;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.ChatColorUtil;

class WorldPackets$7
extends PacketHandlers {
    WorldPackets$7() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_8, Types1_7_6_10.SHORT_POSITION);
        this.handler(wrapper -> {
            for (int i = 0; i < 4; ++i) {
                String line = wrapper.read(Type.STRING);
                line = ChatUtil.jsonToLegacy(line);
                if ((line = ChatUtil.removeUnusedColor(line, '0')).length() > 15 && (line = ChatColorUtil.stripColor(line)).length() > 15) {
                    line = line.substring(0, 15);
                }
                wrapper.write(Type.STRING, line);
            }
        });
    }
}
