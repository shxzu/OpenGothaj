package com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.rewriter;

import com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.Protocol1_20To1_20_2;
import com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.storage.ConfigurationPacketStorage;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.ClientboundPackets1_19_4;

class EntityPacketRewriter1_20_2$2
extends PacketHandlers {
    EntityPacketRewriter1_20_2$2() {
    }

    @Override
    public void register() {
        this.handler(wrapper -> {
            ConfigurationPacketStorage configurationPacketStorage = wrapper.user().remove(ConfigurationPacketStorage.class);
            wrapper.passthrough(Type.INT);
            wrapper.passthrough(Type.BOOLEAN);
            String[] worlds = wrapper.read(Type.STRING_ARRAY);
            int maxPlayers = wrapper.read(Type.VAR_INT);
            int viewDistance = wrapper.read(Type.VAR_INT);
            int simulationDistance = wrapper.read(Type.VAR_INT);
            boolean reducedDebugInfo = wrapper.read(Type.BOOLEAN);
            boolean showRespawnScreen = wrapper.read(Type.BOOLEAN);
            wrapper.read(Type.BOOLEAN);
            String dimensionType = wrapper.read(Type.STRING);
            String world = wrapper.read(Type.STRING);
            long seed = wrapper.read(Type.LONG);
            wrapper.passthrough(Type.BYTE);
            wrapper.passthrough(Type.BYTE);
            wrapper.write(Type.STRING_ARRAY, worlds);
            wrapper.write(Type.NAMED_COMPOUND_TAG, configurationPacketStorage.registry());
            wrapper.write(Type.STRING, dimensionType);
            wrapper.write(Type.STRING, world);
            wrapper.write(Type.LONG, seed);
            wrapper.write(Type.VAR_INT, maxPlayers);
            wrapper.write(Type.VAR_INT, viewDistance);
            wrapper.write(Type.VAR_INT, simulationDistance);
            wrapper.write(Type.BOOLEAN, reducedDebugInfo);
            wrapper.write(Type.BOOLEAN, showRespawnScreen);
            EntityPacketRewriter1_20_2.this.worldDataTrackerHandlerByKey().handle(wrapper);
            wrapper.send(Protocol1_20To1_20_2.class);
            wrapper.cancel();
            if (configurationPacketStorage.enabledFeatures() != null) {
                PacketWrapper featuresPacket = wrapper.create(ClientboundPackets1_19_4.UPDATE_ENABLED_FEATURES);
                featuresPacket.write(Type.STRING_ARRAY, configurationPacketStorage.enabledFeatures());
                featuresPacket.send(Protocol1_20To1_20_2.class);
            }
            configurationPacketStorage.sendQueuedPackets(wrapper.user());
        });
    }
}
