package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.api.rewriter.item.ReplacementItemRewriter;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Environment;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.BaseChunk;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSectionImpl;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_8;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_1;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

class WorldPackets$7
extends PacketHandlers {
    final Protocol1_8To1_9 val$protocol;

    WorldPackets$7(Protocol1_8To1_9 protocol1_8To1_9) {
        this.val$protocol = protocol1_8To1_9;
    }

    @Override
    public void register() {
        this.handler(packetWrapper -> {
            Environment environment = packetWrapper.user().get(ClientWorld.class).getEnvironment();
            Chunk chunk = packetWrapper.read(ChunkType1_9_1.forEnvironment(environment));
            for (ChunkSection section : chunk.getSections()) {
                if (section == null) continue;
                DataPalette palette = section.palette(PaletteType.BLOCKS);
                for (int i = 0; i < palette.size(); ++i) {
                    int block = palette.idByIndex(i);
                    int replacedBlock = ((ReplacementItemRewriter)this.val$protocol.getItemRewriter()).replace(block);
                    palette.setIdByIndex(i, replacedBlock);
                }
            }
            if (chunk.isFullChunk() && chunk.getBitmask() == 0) {
                boolean skylight = environment == Environment.NORMAL;
                ChunkSection[] sections = new ChunkSection[16];
                ChunkSectionImpl section = new ChunkSectionImpl(true);
                sections[0] = section;
                section.palette(PaletteType.BLOCKS).addId(0);
                if (skylight) {
                    section.getLight().setSkyLight(new byte[2048]);
                }
                chunk = new BaseChunk(chunk.getX(), chunk.getZ(), true, false, 1, sections, chunk.getBiomeData(), chunk.getBlockEntities());
            }
            packetWrapper.write(ChunkType1_8.forEnvironment(environment), chunk);
            UserConnection user = packetWrapper.user();
            chunk.getBlockEntities().forEach(nbt -> {
                short action;
                String id;
                if (!(nbt.contains("x") && nbt.contains("y") && nbt.contains("z") && nbt.contains("id"))) {
                    return;
                }
                Position position = new Position((int)((Integer)((Tag)nbt.get("x")).getValue()), (Integer)((Tag)nbt.get("y")).getValue(), (int)((Integer)((Tag)nbt.get("z")).getValue()));
                switch (id = (String)((Tag)nbt.get("id")).getValue()) {
                    case "minecraft:mob_spawner": {
                        action = 1;
                        break;
                    }
                    case "minecraft:command_block": {
                        action = 2;
                        break;
                    }
                    case "minecraft:beacon": {
                        action = 3;
                        break;
                    }
                    case "minecraft:skull": {
                        action = 4;
                        break;
                    }
                    case "minecraft:flower_pot": {
                        action = 5;
                        break;
                    }
                    case "minecraft:banner": {
                        action = 6;
                        break;
                    }
                    default: {
                        return;
                    }
                }
                PacketWrapper updateTileEntity = PacketWrapper.create(9, null, user);
                updateTileEntity.write(Type.POSITION1_8, position);
                updateTileEntity.write(Type.UNSIGNED_BYTE, action);
                updateTileEntity.write(Type.NBT, nbt);
                PacketUtil.sendPacket(updateTileEntity, Protocol1_8To1_9.class, false, false);
            });
        });
    }
}
