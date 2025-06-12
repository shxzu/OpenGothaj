package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.Cooldown;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.util.Pair;
import java.util.ArrayList;
import java.util.UUID;

class EntityPackets$14
extends PacketHandlers {
    EntityPackets$14() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.INT);
        this.handler(packetWrapper -> {
            boolean player = packetWrapper.get(Type.VAR_INT, 0).intValue() == packetWrapper.user().get(EntityTracker.class).getPlayerId();
            int size = packetWrapper.get(Type.INT, 0);
            int removed = 0;
            for (int i = 0; i < size; ++i) {
                String key = packetWrapper.read(Type.STRING);
                boolean skip = !Protocol1_8To1_9.VALID_ATTRIBUTES.contains(key);
                double value = packetWrapper.read(Type.DOUBLE);
                int modifierSize = packetWrapper.read(Type.VAR_INT);
                if (!skip) {
                    packetWrapper.write(Type.STRING, key);
                    packetWrapper.write(Type.DOUBLE, value);
                    packetWrapper.write(Type.VAR_INT, modifierSize);
                } else {
                    ++removed;
                }
                ArrayList<Pair<Byte, Double>> modifiers = new ArrayList<Pair<Byte, Double>>();
                for (int j = 0; j < modifierSize; ++j) {
                    UUID uuid = packetWrapper.read(Type.UUID);
                    double amount = packetWrapper.read(Type.DOUBLE);
                    byte operation = packetWrapper.read(Type.BYTE);
                    modifiers.add(new Pair<Byte, Double>(operation, amount));
                    if (skip) continue;
                    packetWrapper.write(Type.UUID, uuid);
                    packetWrapper.write(Type.DOUBLE, amount);
                    packetWrapper.write(Type.BYTE, operation);
                }
                if (!player || !key.equals("generic.attackSpeed")) continue;
                packetWrapper.user().get(Cooldown.class).setAttackSpeed(value, modifiers);
            }
            packetWrapper.set(Type.INT, 0, size - removed);
        });
    }
}
