package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.packets;

import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.data.WorldNameTracker;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_16;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class EntityPackets1_16$4
extends PacketHandlers {
    EntityPackets1_16$4() {
    }

    @Override
    public void register() {
        this.map(Type.INT);
        this.map(Type.UNSIGNED_BYTE);
        this.read(Type.BYTE);
        this.read(Type.STRING_ARRAY);
        this.read(Type.NAMED_COMPOUND_TAG);
        this.map(EntityPackets1_16.this.dimensionTransformer);
        this.handler(wrapper -> {
            WorldNameTracker worldNameTracker = wrapper.user().get(WorldNameTracker.class);
            worldNameTracker.setWorldName(wrapper.read(Type.STRING));
        });
        this.map(Type.LONG);
        this.map(Type.UNSIGNED_BYTE);
        this.handler(wrapper -> {
            ClientWorld clientChunks = wrapper.user().get(ClientWorld.class);
            clientChunks.setEnvironment(wrapper.get(Type.INT, 1));
            EntityPackets1_16.this.tracker(wrapper.user()).addEntity(wrapper.get(Type.INT, 0), EntityTypes1_16.PLAYER);
            wrapper.write(Type.STRING, "default");
            wrapper.passthrough(Type.VAR_INT);
            wrapper.passthrough(Type.BOOLEAN);
            wrapper.passthrough(Type.BOOLEAN);
            wrapper.read(Type.BOOLEAN);
            if (wrapper.read(Type.BOOLEAN).booleanValue()) {
                wrapper.set(Type.STRING, 0, "flat");
            }
        });
    }
}
