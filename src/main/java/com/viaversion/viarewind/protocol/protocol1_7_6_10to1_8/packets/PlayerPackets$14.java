package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.provider.TitleRenderProvider;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.UUID;

class PlayerPackets$14
extends PacketHandlers {
    PlayerPackets$14() {
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            packetWrapper.cancel();
            TitleRenderProvider titleRenderProvider = Via.getManager().getProviders().get(TitleRenderProvider.class);
            if (titleRenderProvider == null) {
                return;
            }
            int action = packetWrapper.read(Type.VAR_INT);
            UUID uuid = packetWrapper.user().getProtocolInfo().getUuid();
            switch (action) {
                case 0: {
                    titleRenderProvider.setTitle(uuid, packetWrapper.read(Type.STRING));
                    break;
                }
                case 1: {
                    titleRenderProvider.setSubTitle(uuid, packetWrapper.read(Type.STRING));
                    break;
                }
                case 2: {
                    titleRenderProvider.setTimings(uuid, packetWrapper.read(Type.INT), packetWrapper.read(Type.INT), packetWrapper.read(Type.INT));
                    break;
                }
                case 3: {
                    titleRenderProvider.clear(uuid);
                    break;
                }
                case 4: {
                    titleRenderProvider.reset(uuid);
                }
            }
        });
    }
}
