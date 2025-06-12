package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

class WorldPackets$1
extends PacketHandlers {
    WorldPackets$1() {
    }

    @Override
    public void register() {
        this.map(Type.POSITION1_8);
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.NAMED_COMPOUND_TAG);
        this.handler(packetWrapper -> {
            CompoundTag spawnData;
            CompoundTag tag = packetWrapper.get(Type.NAMED_COMPOUND_TAG, 0);
            if (tag != null && tag.contains("SpawnData") && (spawnData = (CompoundTag)tag.get("SpawnData")).contains("id")) {
                String entity = (String)((Tag)spawnData.get("id")).getValue();
                tag.remove("SpawnData");
                tag.put("entityId", new StringTag(entity));
            }
        });
    }
}
