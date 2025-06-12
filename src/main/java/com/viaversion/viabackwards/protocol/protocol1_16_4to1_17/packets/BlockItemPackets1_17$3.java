package com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.packets;

import com.viaversion.viabackwards.protocol.protocol1_16_4to1_17.Protocol1_16_4To1_17;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import java.util.ArrayList;
import java.util.BitSet;

class BlockItemPackets1_17$3
extends PacketHandlers {
    BlockItemPackets1_17$3() {
    }

    @Override
    public void register() {
        this.map(Type.VAR_INT);
        this.map(Type.VAR_INT);
        this.map(Type.BOOLEAN);
        this.handler(wrapper -> {
            Object tracker = wrapper.user().getEntityTracker(Protocol1_16_4To1_17.class);
            int startFromSection = Math.max(0, -(tracker.currentMinY() >> 4));
            long[] skyLightMask = wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
            long[] blockLightMask = wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
            int cutSkyLightMask = BlockItemPackets1_17.this.cutLightMask(skyLightMask, startFromSection);
            int cutBlockLightMask = BlockItemPackets1_17.this.cutLightMask(blockLightMask, startFromSection);
            wrapper.write(Type.VAR_INT, cutSkyLightMask);
            wrapper.write(Type.VAR_INT, cutBlockLightMask);
            long[] emptySkyLightMask = wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
            long[] emptyBlockLightMask = wrapper.read(Type.LONG_ARRAY_PRIMITIVE);
            wrapper.write(Type.VAR_INT, BlockItemPackets1_17.this.cutLightMask(emptySkyLightMask, startFromSection));
            wrapper.write(Type.VAR_INT, BlockItemPackets1_17.this.cutLightMask(emptyBlockLightMask, startFromSection));
            this.writeLightArrays(wrapper, BitSet.valueOf(skyLightMask), cutSkyLightMask, startFromSection, tracker.currentWorldSectionHeight());
            this.writeLightArrays(wrapper, BitSet.valueOf(blockLightMask), cutBlockLightMask, startFromSection, tracker.currentWorldSectionHeight());
        });
    }

    private void writeLightArrays(PacketWrapper wrapper, BitSet bitMask, int cutBitMask, int startFromSection, int sectionHeight) throws Exception {
        int i;
        wrapper.read(Type.VAR_INT);
        ArrayList<byte[]> light = new ArrayList<byte[]>();
        for (i = 0; i < startFromSection; ++i) {
            if (!bitMask.get(i)) continue;
            wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
        }
        for (i = 0; i < 18; ++i) {
            if (!this.isSet(cutBitMask, i)) continue;
            light.add(wrapper.read(Type.BYTE_ARRAY_PRIMITIVE));
        }
        for (i = startFromSection + 18; i < sectionHeight + 2; ++i) {
            if (!bitMask.get(i)) continue;
            wrapper.read(Type.BYTE_ARRAY_PRIMITIVE);
        }
        for (byte[] bytes : light) {
            wrapper.write(Type.BYTE_ARRAY_PRIMITIVE, bytes);
        }
    }

    private boolean isSet(int mask, int i) {
        return (mask & 1 << i) != 0;
    }
}
