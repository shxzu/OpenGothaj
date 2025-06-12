package com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.packets;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.protocol.protocol1_16_1to1_16_2.storage.BiomeStorage;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16_2;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.packets.EntityPackets;

class EntityPackets1_16_2$1
extends PacketHandlers {
    EntityPackets1_16_2$1() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.handler(wrapper -> {
            boolean hardcore = wrapper.read(Type.BOOLEAN);
            short gamemode = wrapper.read(Type.BYTE).byteValue();
            if (hardcore) {
                gamemode = (short)(gamemode | 8);
            }
            wrapper.write(Type.UNSIGNED_BYTE, gamemode);
        });
        this.map(Type.BYTE);
        this.map(Type.STRING_ARRAY);
        this.handler(wrapper -> {
            CompoundTag registry = wrapper.read(Type.NAMED_COMPOUND_TAG);
            if (wrapper.user().getProtocolInfo().getProtocolVersion() <= ProtocolVersion.v1_15_2.getVersion()) {
                CompoundTag biomeRegistry = (CompoundTag)registry.get("minecraft:worldgen/biome");
                ListTag biomes = (ListTag)biomeRegistry.get("value");
                BiomeStorage biomeStorage = wrapper.user().get(BiomeStorage.class);
                biomeStorage.clear();
                for (Tag biome : biomes) {
                    CompoundTag biomeCompound = (CompoundTag)biome;
                    StringTag name = (StringTag)biomeCompound.get("name");
                    NumberTag id = (NumberTag)biomeCompound.get("id");
                    biomeStorage.addBiome(name.getValue(), id.asInt());
                }
            } else if (!EntityPackets1_16_2.this.warned) {
                EntityPackets1_16_2.this.warned = true;
                ViaBackwards.getPlatform().getLogger().warning("1.16 and 1.16.1 clients are only partially supported and may have wrong biomes displayed.");
            }
            wrapper.write(Type.NAMED_COMPOUND_TAG, EntityPackets.DIMENSIONS_TAG);
            CompoundTag dimensionData = wrapper.read(Type.NAMED_COMPOUND_TAG);
            wrapper.write(Type.STRING, EntityPackets1_16_2.this.getDimensionFromData(dimensionData));
        });
        this.map(Type.STRING);
        this.map(Type.LONG);
        this.handler(wrapper -> {
            int maxPlayers = wrapper.read(Type.VAR_INT);
            wrapper.write(Type.UNSIGNED_BYTE, (short)Math.min(maxPlayers, 255));
        });
        this.handler(EntityPackets1_16_2.this.getTrackerHandler(EntityTypes1_16_2.PLAYER, Type.INT));
    }
}
