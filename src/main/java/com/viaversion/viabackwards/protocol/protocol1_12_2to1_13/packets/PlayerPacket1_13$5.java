package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.utils.ChatUtil;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;

class PlayerPacket1_13$5
extends PacketHandlers {
    PlayerPacket1_13$5() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map(Type.BYTE);
        this.handler(wrapper -> {
            byte action = wrapper.get(Type.BYTE, 0);
            if (action == 0 || action == 2) {
                JsonElement displayName = wrapper.read(Type.COMPONENT);
                String legacyTextDisplayName = ((Protocol1_12_2To1_13)PlayerPacket1_13.this.protocol).jsonToLegacy(displayName);
                wrapper.write(Type.STRING, ChatUtil.fromLegacy(legacyTextDisplayName, 'f', 32));
                byte flags = wrapper.read(Type.BYTE);
                String nameTagVisibility = wrapper.read(Type.STRING);
                String collisionRule = wrapper.read(Type.STRING);
                int colour = wrapper.read(Type.VAR_INT);
                if (colour == 21) {
                    colour = -1;
                }
                JsonElement prefixComponent = wrapper.read(Type.COMPONENT);
                JsonElement suffixComponent = wrapper.read(Type.COMPONENT);
                String prefix = ((Protocol1_12_2To1_13)PlayerPacket1_13.this.protocol).jsonToLegacy(prefixComponent);
                if (ViaBackwards.getConfig().addTeamColorTo1_13Prefix()) {
                    prefix = prefix + "§" + (colour > -1 && colour <= 15 ? Integer.toHexString(colour) : "r");
                }
                String suffix = ((Protocol1_12_2To1_13)PlayerPacket1_13.this.protocol).jsonToLegacy(suffixComponent);
                wrapper.write(Type.STRING, ChatUtil.fromLegacyPrefix(prefix, 'f', 16));
                wrapper.write(Type.STRING, ChatUtil.fromLegacy(suffix, '\u0000', 16));
                wrapper.write(Type.BYTE, flags);
                wrapper.write(Type.STRING, nameTagVisibility);
                wrapper.write(Type.STRING, collisionRule);
                wrapper.write(Type.BYTE, (byte)colour);
            }
            if (action == 0 || action == 3 || action == 4) {
                wrapper.passthrough(Type.STRING_ARRAY);
            }
        });
    }
}
