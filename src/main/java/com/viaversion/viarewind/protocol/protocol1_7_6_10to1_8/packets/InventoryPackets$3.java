package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.model.FurnaceData;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.InventoryTracker;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;

class InventoryPackets$3
extends PacketHandlers {
    InventoryPackets$3() {
    }

    @Override
    public void register() {
        this.map(Type.UNSIGNED_BYTE);
        this.map(Type.SHORT);
        this.map(Type.SHORT);
        this.handler(wrapper -> {
            InventoryTracker windowTracker = wrapper.user().get(InventoryTracker.class);
            short windowId = wrapper.get(Type.UNSIGNED_BYTE, 0);
            short windowType = windowTracker.get(windowId);
            short progressBarId = wrapper.get(Type.SHORT, 0);
            short progress = wrapper.get(Type.SHORT, 1);
            if (windowType == 2) {
                FurnaceData furnace = windowTracker.getFurnaceData().computeIfAbsent(windowId, x -> new FurnaceData());
                if (progressBarId == 0 || progressBarId == 1) {
                    if (progressBarId == 0) {
                        furnace.fuelLeft = progress;
                    } else {
                        furnace.maxFuel = progress;
                    }
                    if (furnace.maxFuel == 0) {
                        wrapper.cancel();
                        return;
                    }
                    progress = (short)(200 * furnace.fuelLeft / furnace.maxFuel);
                    wrapper.set(Type.SHORT, 0, (short)1);
                    wrapper.set(Type.SHORT, 1, progress);
                } else if (progressBarId == 2 || progressBarId == 3) {
                    if (progressBarId == 2) {
                        furnace.progress = progress;
                    } else {
                        furnace.maxProgress = progress;
                    }
                    if (furnace.maxProgress == 0) {
                        wrapper.cancel();
                        return;
                    }
                    progress = (short)(200 * furnace.progress / furnace.maxProgress);
                    wrapper.set(Type.SHORT, 0, (short)0);
                    wrapper.set(Type.SHORT, 1, progress);
                }
            } else if (windowType == 4 && progressBarId > 2) {
                wrapper.cancel();
            } else if (windowType == 8) {
                windowTracker.levelCost = progress;
                windowTracker.anvilId = windowId;
            }
        });
    }
}
