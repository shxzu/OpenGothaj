package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.Scoreboard;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.ArrayList;

class ScoreboardPackets$4
extends PacketHandlers {
    ScoreboardPackets$4() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.handler(packetWrapper -> {
            String team = packetWrapper.get(Type.STRING, 0);
            if (team == null) {
                packetWrapper.cancel();
                return;
            }
            byte mode = packetWrapper.passthrough(Type.BYTE);
            Scoreboard scoreboard = packetWrapper.user().get(Scoreboard.class);
            if (mode != 0 && !scoreboard.teamExists(team)) {
                packetWrapper.cancel();
                return;
            }
            if (mode == 0 && scoreboard.teamExists(team)) {
                scoreboard.removeTeam(team);
                PacketWrapper remove = PacketWrapper.create(62, null, packetWrapper.user());
                remove.write(Type.STRING, team);
                remove.write(Type.BYTE, (byte)1);
                PacketUtil.sendPacket(remove, Protocol1_7_6_10To1_8.class, true, true);
            }
            if (mode == 0) {
                scoreboard.addTeam(team);
            } else if (mode == 1) {
                scoreboard.removeTeam(team);
            }
            if (mode == 0 || mode == 2) {
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.passthrough(Type.STRING);
                packetWrapper.passthrough(Type.BYTE);
                packetWrapper.read(Type.STRING);
                byte color = packetWrapper.read(Type.BYTE);
                if (mode == 2 && scoreboard.getTeamColor(team).get() != color) {
                    String username = packetWrapper.user().getProtocolInfo().getUsername();
                    String sidebar = scoreboard.getColorDependentSidebar().get(color);
                    PacketWrapper sidebarPacket = packetWrapper.create(61);
                    sidebarPacket.write(Type.BYTE, (byte)1);
                    sidebarPacket.write(Type.STRING, sidebar == null ? "" : sidebar);
                    PacketUtil.sendPacket(sidebarPacket, Protocol1_7_6_10To1_8.class);
                }
                scoreboard.setTeamColor(team, color);
            }
            if (mode == 0 || mode == 3 || mode == 4) {
                byte color = scoreboard.getTeamColor(team).get();
                String[] entries = packetWrapper.read(Type.STRING_ARRAY);
                ArrayList<String> entryList = new ArrayList<String>();
                for (int i = 0; i < entries.length; ++i) {
                    String entry = entries[i];
                    String username = packetWrapper.user().getProtocolInfo().getUsername();
                    if (mode == 4) {
                        if (!scoreboard.isPlayerInTeam(entry, team)) continue;
                        scoreboard.removePlayerFromTeam(entry, team);
                        if (entry.equals(username)) {
                            PacketWrapper sidebarPacket = packetWrapper.create(61);
                            sidebarPacket.write(Type.BYTE, (byte)1);
                            sidebarPacket.write(Type.STRING, scoreboard.getColorIndependentSidebar() == null ? "" : scoreboard.getColorIndependentSidebar());
                            PacketUtil.sendPacket(sidebarPacket, Protocol1_7_6_10To1_8.class);
                        }
                    } else {
                        scoreboard.addPlayerToTeam(entry, team);
                        if (entry.equals(username) && scoreboard.getColorDependentSidebar().containsKey(color)) {
                            PacketWrapper displayObjective = packetWrapper.create(61);
                            displayObjective.write(Type.BYTE, (byte)1);
                            displayObjective.write(Type.STRING, scoreboard.getColorDependentSidebar().get(color));
                            PacketUtil.sendPacket(displayObjective, Protocol1_7_6_10To1_8.class);
                        }
                    }
                    entryList.add(entry);
                }
                packetWrapper.write(Type.SHORT, (short)entryList.size());
                for (String entry : entryList) {
                    packetWrapper.write(Type.STRING, entry);
                }
            }
        });
    }
}
