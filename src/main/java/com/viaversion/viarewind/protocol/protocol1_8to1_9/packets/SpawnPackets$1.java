package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.api.rewriter.item.Replacement;
import com.viaversion.viarewind.api.rewriter.item.ReplacementItemRewriter;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.entityreplacement.ShulkerBulletModel;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_10;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class SpawnPackets$1
extends PacketHandlers {
    final Protocol1_8To1_9 val$protocol;

    SpawnPackets$1(Protocol1_8To1_9 protocol1_8To1_9) {
        this.val$protocol = protocol1_8To1_9;
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.read(Type.UUID);
        this.map(Type.BYTE);
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.map(Type.DOUBLE, Protocol1_8To1_9.TO_OLD_INT);
        this.map(Type.BYTE);
        this.map(Type.BYTE);
        this.map(Type.INT);
        this.handler(packetWrapper -> {
            int entityId = packetWrapper.get(Type.VAR_INT, 0);
            byte typeId = packetWrapper.get(Type.BYTE, 0);
            EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
            EntityTypes1_10.EntityType type = EntityTypes1_10.getTypeFromId(typeId, true);
            if (typeId == 3 || typeId == 91 || typeId == 92 || typeId == 93) {
                packetWrapper.cancel();
                return;
            }
            if (type == null) {
                ViaRewind.getPlatform().getLogger().warning("[ViaRewind] Unhandled Spawn Object Type: " + typeId);
                packetWrapper.cancel();
                return;
            }
            int x = packetWrapper.get(Type.INT, 0);
            int y = packetWrapper.get(Type.INT, 1);
            int z = packetWrapper.get(Type.INT, 2);
            if (type.is((EntityType)EntityTypes1_10.EntityType.BOAT)) {
                byte yaw = packetWrapper.get(Type.BYTE, 1);
                yaw = (byte)(yaw - 64);
                packetWrapper.set(Type.BYTE, 1, yaw);
                packetWrapper.set(Type.INT, 1, y += 10);
            } else if (type.is((EntityType)EntityTypes1_10.EntityType.SHULKER_BULLET)) {
                packetWrapper.cancel();
                ShulkerBulletModel shulkerBulletReplacement = new ShulkerBulletModel(packetWrapper.user(), this.val$protocol, entityId);
                shulkerBulletReplacement.updateReplacementPosition((double)x / 32.0, (double)y / 32.0, (double)z / 32.0);
                tracker.addEntityReplacement(shulkerBulletReplacement);
                return;
            }
            int data = packetWrapper.get(Type.INT, 3);
            if (type.isOrHasParent(EntityTypes1_10.EntityType.ARROW) && data != 0) {
                packetWrapper.set(Type.INT, 3, --data);
            }
            if (type.is((EntityType)EntityTypes1_10.EntityType.FALLING_BLOCK)) {
                int blockId = data & 0xFFF;
                int blockData = data >> 12 & 0xF;
                Replacement replace = ((ReplacementItemRewriter)this.val$protocol.getItemRewriter()).replace(blockId, blockData);
                if (replace != null) {
                    packetWrapper.set(Type.INT, 3, replace.getId() | replace.replaceData(data) << 12);
                }
            }
            if (data > 0) {
                packetWrapper.passthrough(Type.SHORT);
                packetWrapper.passthrough(Type.SHORT);
                packetWrapper.passthrough(Type.SHORT);
            } else {
                short vX = packetWrapper.read(Type.SHORT);
                short vY = packetWrapper.read(Type.SHORT);
                short vZ = packetWrapper.read(Type.SHORT);
                PacketWrapper velocityPacket = PacketWrapper.create(18, null, packetWrapper.user());
                velocityPacket.write(Type.VAR_INT, entityId);
                velocityPacket.write(Type.SHORT, vX);
                velocityPacket.write(Type.SHORT, vY);
                velocityPacket.write(Type.SHORT, vZ);
                PacketUtil.sendPacket(velocityPacket, Protocol1_8To1_9.class);
            }
            tracker.getClientEntityTypes().put(entityId, type);
            tracker.sendMetadataBuffer(entityId);
        });
    }
}
