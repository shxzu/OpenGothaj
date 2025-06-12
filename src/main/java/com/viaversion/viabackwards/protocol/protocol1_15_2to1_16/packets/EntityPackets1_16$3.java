package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.WorldNameTracker;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.ClientboundPackets1_15;

class EntityPackets1_16$3
extends PacketHandlers {
    EntityPackets1_16$3() {
    }

    @Override
    public void register() {
        this.map(EntityPackets1_16.this.dimensionTransformer);
        this.handler(wrapper -> {
            WorldNameTracker worldNameTracker = wrapper.user().get(WorldNameTracker.class);
            String nextWorldName = wrapper.read(Type.STRING);
            wrapper.passthrough(Type.LONG);
            wrapper.passthrough(Type.UNSIGNED_BYTE);
            wrapper.read(Type.BYTE);
            ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
            int dimension = wrapper.get(Type.INT, 0);
            if (clientWorld.getEnvironment() != null && dimension == clientWorld.getEnvironment().id() && (wrapper.user().isClientSide() || Via.getPlatform().isProxy() || wrapper.user().getProtocolInfo().getProtocolVersion() <= ProtocolVersion.v1_12_2.getVersion() || !nextWorldName.equals(worldNameTracker.getWorldName()))) {
                PacketWrapper packet = wrapper.create(ClientboundPackets1_15.RESPAWN);
                packet.write(Type.INT, dimension == 0 ? -1 : 0);
                packet.write(Type.LONG, 0L);
                packet.write(Type.UNSIGNED_BYTE, (short)0);
                packet.write(Type.STRING, "default");
                packet.send(Protocol1_15_2To1_16.class);
            }
            clientWorld.setEnvironment(dimension);
            wrapper.write(Type.STRING, "default");
            wrapper.read(Type.BOOLEAN);
            if (wrapper.read(Type.BOOLEAN).booleanValue()) {
                wrapper.set(Type.STRING, 0, "flat");
            }
            wrapper.read(Type.BOOLEAN);
            worldNameTracker.setWorldName(nextWorldName);
        });
    }
}
