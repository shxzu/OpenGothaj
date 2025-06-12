package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_8;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import java.util.ArrayList;

class WorldPackets$6
extends PacketHandlers {
    WorldPackets$6() {
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            Environment environment = packetWrapper.user().get(ClientWorld.class).getEnvironment();
            int chunkX = packetWrapper.read(Type.INT);
            int chunkZ = packetWrapper.read(Type.INT);
            packetWrapper.write(ChunkType1_8.forEnvironment(environment), new BaseChunk(chunkX, chunkZ, true, false, 0, new ChunkSection[16], null, new ArrayList<CompoundTag>()));
        });
    }
}
