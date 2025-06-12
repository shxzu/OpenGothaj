package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.api.rewriter.item.Replacement;
import com.viaversion.viarewind.api.rewriter.item.ReplacementItemRewriter;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.Protocol1_7_6_10To1_8;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.model.VirtualHologramEntity;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker1_7_6_10;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class SpawnPackets$2
extends PacketHandlers {
    final Protocol1_7_6_10To1_8 val$protocol;

    SpawnPackets$2(Protocol1_7_6_10To1_8 protocol1_7_6_10To1_8) {
        this.val$protocol = protocol1_7_6_10To1_8;
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.BYTE);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.INT);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.INT);
        this.handler(wrapper -> {
            EntityTracker1_7_6_10 tracker = wrapper.user().get(EntityTracker1_7_6_10.class);
            EntityTypes1_10.EntityType type = EntityTypes1_10.getTypeFromId(wrapper.get(Type.BYTE, 0).byteValue(), true);
            int entityId = wrapper.get(Type.VAR_INT, 0);
            int x = wrapper.get(Type.INT, 0);
            int y = wrapper.get(Type.INT, 1);
            int z = wrapper.get(Type.INT, 2);
            byte pitch = wrapper.get(Type.BYTE, 1);
            byte yaw = wrapper.get(Type.BYTE, 2);
            int data = wrapper.get(Type.INT, 3);
            if (type == EntityTypes1_10.ObjectType.ITEM_FRAME.getType()) {
                switch (yaw) {
                    case -128: {
                        z += 32;
                        yaw = 0;
                        break;
                    }
                    case -64: {
                        x -= 32;
                        yaw = -64;
                        break;
                    }
                    case 0: {
                        z -= 32;
                        yaw = -128;
                        break;
                    }
                    case 64: {
                        x += 32;
                        yaw = 64;
                    }
                }
            } else if (type == EntityTypes1_10.ObjectType.ARMOR_STAND.getType()) {
                wrapper.cancel();
                VirtualHologramEntity hologram = new VirtualHologramEntity(wrapper.user(), this.val$protocol.getMetadataRewriter(), entityId);
                hologram.updateReplacementPosition((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                hologram.setYawPitch((float)yaw * 360.0f / 256.0f, (float)pitch * 360.0f / 256.0f);
                hologram.setHeadYaw((float)yaw * 360.0f / 256.0f);
                tracker.trackHologram(entityId, hologram);
            }
            wrapper.set(Type.INT, 0, x);
            wrapper.set(Type.INT, 1, y);
            wrapper.set(Type.INT, 2, z);
            wrapper.set(Type.BYTE, 2, yaw);
            tracker.addEntity(entityId, type);
            if (type != null && type.isOrHasParent(EntityTypes1_10.EntityType.FALLING_BLOCK)) {
                int blockId = data & 0xFFF;
                int blockData = data >> 12 & 0xF;
                Replacement replace = ((ReplacementItemRewriter)this.val$protocol.getItemRewriter()).replace(blockId, blockData);
                if (replace != null) {
                    blockId = replace.getId();
                    blockData = replace.replaceData(blockData);
                }
                data = blockId | blockData << 16;
                wrapper.set(Type.INT, 3, data);
            }
            if (data > 0) {
                wrapper.passthrough(Type.SHORT);
                wrapper.passthrough(Type.SHORT);
                wrapper.passthrough(Type.SHORT);
            }
        });
    }
}
