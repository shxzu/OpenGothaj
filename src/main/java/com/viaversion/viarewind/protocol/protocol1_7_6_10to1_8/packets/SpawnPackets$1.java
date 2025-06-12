package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_2_5to1_7_6_10.ClientboundPackets1_7_2_5;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.types.Types1_7_6_10;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.version.Types1_8;
import java.util.List;
import java.util.UUID;

class SpawnPackets$1
extends PacketHandlers {
    final Protocol1_7_6_10To1_8 val$protocol;

    SpawnPackets$1(Protocol1_7_6_10To1_8 protocol1_7_6_10To1_8) {
        this.val$protocol = protocol1_7_6_10To1_8;
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.handler(wrapper -> {
            UUID uuid = wrapper.read(Type.UUID);
            wrapper.write(Type.STRING, uuid.toString());
            GameProfileStorage gameProfileStorage = wrapper.user().get(GameProfileStorage.class);
            GameProfileStorage.GameProfile gameProfile = gameProfileStorage.get(uuid);
            if (gameProfile == null) {
                wrapper.write(Type.STRING, "");
                wrapper.write(Type.VAR_INT, 0);
            } else {
                wrapper.write(Type.STRING, gameProfile.name.length() > 16 ? gameProfile.name.substring(0, 16) : gameProfile.name);
                wrapper.write(Type.VAR_INT, gameProfile.properties.size());
                for (GameProfileStorage.Property property : gameProfile.properties) {
                    wrapper.write(Type.STRING, property.name);
                    wrapper.write(Type.STRING, property.value);
                    wrapper.write(Type.STRING, property.signature == null ? "" : property.signature);
                }
            }
            int entityId = wrapper.get(Type.VAR_INT, 0);
            EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
            if (gameProfile != null && gameProfile.gamemode == 3) {
                for (short i = 0; i < 5; i = (short)(i + 1)) {
                    PacketWrapper entityEquipment = PacketWrapper.create(ClientboundPackets1_7_2_5.ENTITY_EQUIPMENT, wrapper.user());
                    entityEquipment.write(Type.INT, entityId);
                    entityEquipment.write(Type.SHORT, i);
                    entityEquipment.write(Types1_7_6_10.COMPRESSED_NBT_ITEM, i == 4 ? gameProfile.getSkull() : null);
                    entityEquipment.scheduleSend(Protocol1_7_6_10To1_8.class, true);
                }
            }
            tracker.addPlayer(entityId, uuid);
        });
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.SHORT);
        this.map(Types1_8.METADATA_LIST, Types1_7_6_10.METADATA_LIST);
        this.handler(wrapper -> {
            List<Metadata> metadata = wrapper.get(Types1_7_6_10.METADATA_LIST, 0);
            this.val$protocol.getMetadataRewriter().transform(EntityTypes1_10.EntityType.PLAYER, metadata);
            wrapper.set(Types1_7_6_10.METADATA_LIST, 0, metadata);
        });
        this.handler(wrapper -> {
            EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
            tracker.addEntity(wrapper.get(Type.VAR_INT, 0), EntityTypes1_10.EntityType.PLAYER);
        });
    }
}
