package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10.ClientboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerSessionStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viarewind.utils.ChatUtil;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.gson.JsonElement;
import java.util.UUID;

class PlayerPackets$10
extends PacketHandlers {
    PlayerPackets$10() {
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            packetWrapper.cancel();
            int action = packetWrapper.read(Type.VAR_INT);
            int count = packetWrapper.read(Type.VAR_INT);
            GameProfileStorage gameProfileStorage = packetWrapper.user().get(GameProfileStorage.class);
            for (int i = 0; i < count; ++i) {
                GameProfileStorage.GameProfile gameProfile;
                GameProfileStorage.GameProfile gameProfile2;
                UUID uuid = packetWrapper.read(Type.UUID);
                if (action == 0) {
                    int ping;
                    String name = packetWrapper.read(Type.STRING);
                    gameProfile2 = gameProfileStorage.get(uuid);
                    if (gameProfile2 == null) {
                        gameProfile2 = gameProfileStorage.put(uuid, name);
                    }
                    int propertyCount = packetWrapper.read(Type.VAR_INT);
                    while (propertyCount-- > 0) {
                        String propertyName = packetWrapper.read(Type.STRING);
                        String propertyValue = packetWrapper.read(Type.STRING);
                        String propertySignature = packetWrapper.read(Type.OPTIONAL_STRING);
                        gameProfile2.properties.add(new GameProfileStorage.Property(propertyName, propertyValue, propertySignature));
                    }
                    int gamemode = packetWrapper.read(Type.VAR_INT);
                    gameProfile2.ping = ping = packetWrapper.read(Type.VAR_INT).intValue();
                    gameProfile2.gamemode = gamemode;
                    JsonElement displayName = packetWrapper.read(Type.OPTIONAL_COMPONENT);
                    if (displayName != null) {
                        gameProfile2.setDisplayName(ChatUtil.jsonToLegacy(displayName));
                    }
                    PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, null, packetWrapper.user());
                    packet.write(Type.STRING, gameProfile2.getDisplayName());
                    packet.write(Type.BOOLEAN, true);
                    packet.write(Type.SHORT, (short)ping);
                    PacketUtil.sendPacket(packet, Protocol1_7_6_10To1_8.class);
                    continue;
                }
                if (action == 1) {
                    int gamemode = packetWrapper.read(Type.VAR_INT);
                    gameProfile2 = gameProfileStorage.get(uuid);
                    if (gameProfile2 == null || gameProfile2.gamemode == gamemode) continue;
                    if (gamemode == 3 || gameProfile2.gamemode == 3) {
                        boolean isOwnPlayer;
                        EntityTracker1_7_6_10 tracker = packetWrapper.user().get(EntityTracker1_7_6_10.class);
                        int entityId = tracker.getPlayerEntityId(uuid);
                        boolean bl = isOwnPlayer = entityId == tracker.getPlayerId();
                        if (entityId != -1) {
                            Item[] equipment = new Item[isOwnPlayer ? 4 : 5];
                            if (gamemode == 3) {
                                equipment[isOwnPlayer ? 3 : 4] = gameProfile2.getSkull();
                            } else {
                                for (int j = 0; j < equipment.length; ++j) {
                                    equipment[j] = packetWrapper.user().get(PlayerSessionStorage.class).getPlayerEquipment(uuid, j);
                                }
                            }
                            for (short slot = 0; slot < equipment.length; slot = (short)(slot + 1)) {
                                PacketWrapper equipmentPacket = PacketWrapper.create(ClientboundPackets1_7_2_5.ENTITY_EQUIPMENT, packetWrapper.user());
                                equipmentPacket.write(Type.INT, entityId);
                                equipmentPacket.write(Type.SHORT, slot);
                                equipmentPacket.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, equipment[slot]);
                                PacketUtil.sendPacket(equipmentPacket, Protocol1_7_6_10To1_8.class);
                            }
                        }
                    }
                    gameProfile2.gamemode = gamemode;
                    continue;
                }
                if (action == 2) {
                    int ping = packetWrapper.read(Type.VAR_INT);
                    gameProfile2 = gameProfileStorage.get(uuid);
                    if (gameProfile2 == null) continue;
                    PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, null, packetWrapper.user());
                    packet.write(Type.STRING, gameProfile2.getDisplayName());
                    packet.write(Type.BOOLEAN, false);
                    packet.write(Type.SHORT, (short)gameProfile2.ping);
                    PacketUtil.sendPacket(packet, Protocol1_7_6_10To1_8.class);
                    gameProfile2.ping = ping;
                    packet = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, null, packetWrapper.user());
                    packet.write(Type.STRING, gameProfile2.getDisplayName());
                    packet.write(Type.BOOLEAN, true);
                    packet.write(Type.SHORT, (short)ping);
                    PacketUtil.sendPacket(packet, Protocol1_7_6_10To1_8.class);
                    continue;
                }
                if (action == 3) {
                    JsonElement displayNameComponent = packetWrapper.read(Type.OPTIONAL_COMPONENT);
                    String displayName = displayNameComponent != null ? ChatUtil.jsonToLegacy(displayNameComponent) : null;
                    GameProfileStorage.GameProfile gameProfile3 = gameProfileStorage.get(uuid);
                    if (gameProfile3 == null || gameProfile3.displayName == null && displayName == null) continue;
                    PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, null, packetWrapper.user());
                    packet.write(Type.STRING, gameProfile3.getDisplayName());
                    packet.write(Type.BOOLEAN, false);
                    packet.write(Type.SHORT, (short)gameProfile3.ping);
                    PacketUtil.sendPacket(packet, Protocol1_7_6_10To1_8.class);
                    if (gameProfile3.displayName == null && displayName != null || gameProfile3.displayName != null && displayName == null || !gameProfile3.displayName.equals(displayName)) {
                        gameProfile3.setDisplayName(displayName);
                    }
                    packet = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, null, packetWrapper.user());
                    packet.write(Type.STRING, gameProfile3.getDisplayName());
                    packet.write(Type.BOOLEAN, true);
                    packet.write(Type.SHORT, (short)gameProfile3.ping);
                    PacketUtil.sendPacket(packet, Protocol1_7_6_10To1_8.class);
                    continue;
                }
                if (action != 4 || (gameProfile = gameProfileStorage.remove(uuid)) == null) continue;
                PacketWrapper packet = PacketWrapper.create(ClientboundPackets1_7_2_5.PLAYER_INFO, null, packetWrapper.user());
                packet.write(Type.STRING, gameProfile.getDisplayName());
                packet.write(Type.BOOLEAN, false);
                packet.write(Type.SHORT, (short)gameProfile.ping);
                PacketUtil.sendPacket(packet, Protocol1_7_6_10To1_8.class);
            }
        });
    }
}
