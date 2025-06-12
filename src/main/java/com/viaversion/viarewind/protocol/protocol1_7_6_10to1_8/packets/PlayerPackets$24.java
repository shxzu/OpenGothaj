package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class PlayerPackets$24
extends PacketHandlers {
    PlayerPackets$24() {
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            int entityId = packetWrapper.read(Type.INT);
            int animation = packetWrapper.read(Type.BYTE).byteValue();
            if (animation == 1) {
                return;
            }
            packetWrapper.cancel();
            switch (animation) {
                case 104: {
                    animation = 0;
                    break;
                }
                case 105: {
                    animation = 1;
                    break;
                }
                case 3: {
                    animation = 2;
                    break;
                }
                default: {
                    return;
                }
            }
            PacketWrapper entityAction = PacketWrapper.create(11, null, packetWrapper.user());
            entityAction.write(Type.VAR_INT, entityId);
            entityAction.write(Type.VAR_INT, animation);
            entityAction.write(Type.VAR_INT, 0);
            PacketUtil.sendPacket(entityAction, Protocol1_7_6_10To1_8.class, true, true);
        });
    }
}
