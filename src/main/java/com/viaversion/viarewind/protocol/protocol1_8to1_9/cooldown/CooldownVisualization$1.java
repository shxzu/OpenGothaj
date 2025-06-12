package com.viaversion.viarewind.protocol.protocol1_8to1_9.cooldown;

import com.viaversion.viarewind.api.ViaRewindConfig;

class CooldownVisualization$1 {
    static final int[] $SwitchMap$com$viaversion$viarewind$api$ViaRewindConfig$CooldownIndicator;

    static {
        $SwitchMap$com$viaversion$viarewind$api$ViaRewindConfig$CooldownIndicator = new int[ViaRewindConfig.CooldownIndicator.values().length];
        try {
            CooldownVisualization$1.$SwitchMap$com$viaversion$viarewind$api$ViaRewindConfig$CooldownIndicator[ViaRewindConfig.CooldownIndicator.TITLE.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            CooldownVisualization$1.$SwitchMap$com$viaversion$viarewind$api$ViaRewindConfig$CooldownIndicator[ViaRewindConfig.CooldownIndicator.BOSS_BAR.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            CooldownVisualization$1.$SwitchMap$com$viaversion$viarewind$api$ViaRewindConfig$CooldownIndicator[ViaRewindConfig.CooldownIndicator.ACTION_BAR.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
        try {
            CooldownVisualization$1.$SwitchMap$com$viaversion$viarewind$api$ViaRewindConfig$CooldownIndicator[ViaRewindConfig.CooldownIndicator.DISABLED.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {
            // empty catch block
        }
    }
}
