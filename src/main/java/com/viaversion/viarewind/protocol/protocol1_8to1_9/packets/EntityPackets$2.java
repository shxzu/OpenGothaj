package com.viaversion.viarewind.protocol.protocol1_8to1_9.packets;

import com.viaversion.viarewind.api.minecraft.EntityModel;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.Protocol1_8To1_9;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.storage.EntityTracker;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.util.RelativeMoveUtil;
import com.viaversion.viarewind.utils.PacketUtil;
import com.viaversion.viaversion.api.minecraft.Vector;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;

class EntityPackets$2
extends PacketHandlers {
    EntityPackets$2() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.handler(packetWrapper -> {
            int entityId = packetWrapper.get(Type.VAR_INT, 0);
            short relX = packetWrapper.read(Type.SHORT);
            short relY = packetWrapper.read(Type.SHORT);
            short relZ = packetWrapper.read(Type.SHORT);
            EntityTracker tracker = packetWrapper.user().get(EntityTracker.class);
            EntityModel replacement = tracker.getEntityReplacement(entityId);
            if (replacement != null) {
                packetWrapper.cancel();
                replacement.handleOriginalMovementPacket((double)relX / 4096.0, (double)relY / 4096.0, (double)relZ / 4096.0);
                return;
            }
            Vector[] moves = RelativeMoveUtil.calculateRelativeMoves(packetWrapper.user(), entityId, relX, relY, relZ);
            packetWrapper.write(Type.BYTE, (byte)moves[0].blockX());
            packetWrapper.write(Type.BYTE, (byte)moves[0].blockY());
            packetWrapper.write(Type.BYTE, (byte)moves[0].blockZ());
            boolean onGround = packetWrapper.passthrough(Type.BOOLEAN);
            if (moves.length > 1) {
                PacketWrapper secondPacket = PacketWrapper.create(ClientboundPackets1_8.ENTITY_POSITION, null, packetWrapper.user());
                secondPacket.write(Type.VAR_INT, packetWrapper.get(Type.VAR_INT, 0));
                secondPacket.write(Type.BYTE, (byte)moves[1].blockX());
                secondPacket.write(Type.BYTE, (byte)moves[1].blockY());
                secondPacket.write(Type.BYTE, (byte)moves[1].blockZ());
                secondPacket.write(Type.BOOLEAN, onGround);
                PacketUtil.sendPacket(secondPacket, Protocol1_8To1_9.class);
            }
        });
    }
}
