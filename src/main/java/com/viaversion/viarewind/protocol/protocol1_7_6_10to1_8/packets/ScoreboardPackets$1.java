package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.Scoreboard;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.Optional;

class ScoreboardPackets$1
extends PacketHandlers {
    ScoreboardPackets$1() {
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            String name = packetWrapper.passthrough(Type.STRING);
            if (name.length() > 16) {
                name = name.substring(0, 16);
                packetWrapper.set(Type.STRING, 0, name);
            }
            byte mode = packetWrapper.read(Type.BYTE);
            Scoreboard scoreboard = packetWrapper.user().get(Scoreboard.class);
            if (mode == 0) {
                if (scoreboard.objectiveExists(name)) {
                    packetWrapper.cancel();
                    return;
                }
                scoreboard.addObjective(name);
            } else if (mode == 1) {
                String sidebar;
                String username;
                Optional<Byte> color;
                if (!scoreboard.objectiveExists(name)) {
                    packetWrapper.cancel();
                    return;
                }
                if (scoreboard.getColorIndependentSidebar() != null && (color = scoreboard.getPlayerTeamColor(username = packetWrapper.user().getProtocolInfo().getUsername())).isPresent() && name.equals(sidebar = scoreboard.getColorDependentSidebar().get(color.get()))) {
                    PacketWrapper sidebarPacket = PacketWrapper.create(61, null, packetWrapper.user());
                    sidebarPacket.write(Type.BYTE, (byte)1);
                    sidebarPacket.write(Type.STRING, scoreboard.getColorIndependentSidebar());
                    PacketUtil.sendPacket(sidebarPacket, Protocol1_7_6_10To1_8.class);
                }
                scoreboard.removeObjective(name);
            } else if (mode == 2 && !scoreboard.objectiveExists(name)) {
                packetWrapper.cancel();
                return;
            }
            if (mode == 0 || mode == 2) {
                String displayName = packetWrapper.passthrough(Type.STRING);
                if (displayName.length() > 32) {
                    packetWrapper.set(Type.STRING, 1, displayName.substring(0, 32));
                }
                packetWrapper.read(Type.STRING);
            } else {
                packetWrapper.write(Type.STRING, "");
            }
            packetWrapper.write(Type.BYTE, mode);
        });
    }
}
