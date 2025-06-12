package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.Scoreboard;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.Optional;

class ScoreboardPackets$3
extends PacketHandlers {
    ScoreboardPackets$3() {
    }

    @Override
    public void register() {
        this.map(Type.BYTE);
        this.map(Type.STRING);
        this.handler(packetWrapper -> {
            int position = packetWrapper.get(Type.BYTE, 0).byteValue();
            String name = packetWrapper.get(Type.STRING, 0);
            Scoreboard scoreboard = packetWrapper.user().get(Scoreboard.class);
            if (position > 2) {
                byte receiverTeamColor = (byte)(position - 3);
                scoreboard.getColorDependentSidebar().put(receiverTeamColor, name);
                String username = packetWrapper.user().getProtocolInfo().getUsername();
                Optional<Byte> color = scoreboard.getPlayerTeamColor(username);
                position = color.isPresent() && color.get() == receiverTeamColor ? 1 : -1;
            } else if (position == 1) {
                scoreboard.setColorIndependentSidebar(name);
                String username = packetWrapper.user().getProtocolInfo().getUsername();
                Optional<Byte> color = scoreboard.getPlayerTeamColor(username);
                if (color.isPresent() && scoreboard.getColorDependentSidebar().containsKey(color.get())) {
                    position = -1;
                }
            }
            if (position == -1) {
                packetWrapper.cancel();
                return;
            }
            packetWrapper.set(Type.BYTE, 0, (byte)position);
        });
    }
}
