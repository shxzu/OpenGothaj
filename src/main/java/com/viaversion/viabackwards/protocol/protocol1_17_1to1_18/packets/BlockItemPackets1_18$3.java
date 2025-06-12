package com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.packets;

import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.Protocol1_17_1To1_18;
import com.viaversion.viabackwards.protocol.protocol1_17_1to1_18.data.BlockEntityIds;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.util.Key;

class BlockItemPackets1_18$3
extends PacketHandlers {
    BlockItemPackets1_18$3() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_14);
        this.handler(wrapper -> {
            int id = wrapper.read(Type.VAR_INT);
            CompoundTag tag = wrapper.read(Type.NAMED_COMPOUND_TAG);
            int mappedId = BlockEntityIds.mappedId(id);
            if (mappedId == -1) {
                wrapper.cancel();
                return;
            }
            String identifier = (String)((Protocol1_17_1To1_18)BlockItemPackets1_18.this.protocol).getMappingData().blockEntities().get(id);
            if (identifier == null) {
                wrapper.cancel();
                return;
            }
            CompoundTag newTag = tag == null ? new CompoundTag() : tag;
            Position pos = wrapper.get(Type.POSITION1_14, 0);
            newTag.put("id", new StringTag(Key.namespaced(identifier)));
            newTag.put("x", new IntTag(pos.x()));
            newTag.put("y", new IntTag(pos.y()));
            newTag.put("z", new IntTag(pos.z()));
            BlockItemPackets1_18.this.handleSpawner(id, newTag);
            wrapper.write(Type.UNSIGNED_BYTE, (short)mappedId);
            wrapper.write(Type.NAMED_COMPOUND_TAG, newTag);
        });
    }
}
