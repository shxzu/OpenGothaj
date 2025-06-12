package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.Scoreboard;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.ChatColorUtil;

class ScoreboardPackets$2
extends PacketHandlers {
    ScoreboardPackets$2() {
    }

    @Override
    public void register() {
        this.map(Type.STRING);
        this.map((Type)Type.VAR_INT, Type.BYTE);
        this.handler(packetWrapper -> {
            Scoreboard scoreboard = packetWrapper.user().get(Scoreboard.class);
            String name = packetWrapper.get(Type.STRING, 0);
            byte mode = packetWrapper.get(Type.BYTE, 0);
            name = mode == 1 ? scoreboard.removeTeamForScore(name) : scoreboard.sendTeamForScore(name);
            if (name.length() > 16 && (name = ChatColorUtil.stripColor(name)).length() > 16) {
                name = name.substring(0, 16);
            }
            packetWrapper.set(Type.STRING, 0, name);
            String objective = packetWrapper.read(Type.STRING);
            if (objective.length() > 16) {
                objective = objective.substring(0, 16);
            }
            if (mode != 1) {
                int score = packetWrapper.read(Type.VAR_INT);
                packetWrapper.write(Type.STRING, objective);
                packetWrapper.write(Type.INT, score);
            }
        });
    }
}
