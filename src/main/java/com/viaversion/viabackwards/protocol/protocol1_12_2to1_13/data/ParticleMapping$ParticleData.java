package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.ParticleMapping;
import com.viaversion.viaversion.api.minecraft.Particle;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import java.util.List;

public final class ParticleMapping$ParticleData {
    private final int historyId;
    private final ParticleMapping.ParticleHandler handler;

    private ParticleMapping$ParticleData(int historyId, ParticleMapping.ParticleHandler handler) {
        this.historyId = historyId;
        this.handler = handler;
    }

    private ParticleMapping$ParticleData(int historyId) {
        this(historyId, (ParticleMapping.ParticleHandler)null);
    }

    public int[] rewriteData(Protocol1_12_2To1_13 protocol, PacketWrapper wrapper) throws Exception {
        if (this.handler == null) {
            return null;
        }
        return this.handler.rewrite(protocol, wrapper);
    }

    public int[] rewriteMeta(Protocol1_12_2To1_13 protocol, List<Particle.ParticleData<?>> data) {
        if (this.handler == null) {
            return null;
        }
        return this.handler.rewrite(protocol, data);
    }

    public int getHistoryId() {
        return this.historyId;
    }

    public ParticleMapping.ParticleHandler getHandler() {
        return this.handler;
    }
}
