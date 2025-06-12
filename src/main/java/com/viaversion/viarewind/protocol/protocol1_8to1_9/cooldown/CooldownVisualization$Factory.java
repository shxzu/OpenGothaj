package com.viaversion.viarewind.protocol.protocol1_8to1_9.cooldown;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.api.ViaRewindConfig;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.cooldown.ActionBarVisualization;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.cooldown.BossBarVisualization;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.cooldown.CooldownVisualization;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.cooldown.DisabledCooldownVisualization;
import com.viaversion.viarewind.protocol.protocol1_8to1_9.cooldown.TitleCooldownVisualization;
import com.viaversion.viaversion.api.connection.UserConnection;

public interface CooldownVisualization$Factory {
    public static final CooldownVisualization$Factory DISABLED = user -> new DisabledCooldownVisualization();

    public CooldownVisualization create(UserConnection var1);

    public static CooldownVisualization$Factory fromConfiguration() {
        try {
            return CooldownVisualization$Factory.fromIndicator(ViaRewind.getConfig().getCooldownIndicator());
        }
        catch (IllegalArgumentException e) {
            ViaRewind.getPlatform().getLogger().warning("Invalid cooldown-indicator setting");
            return DISABLED;
        }
    }

    public static CooldownVisualization$Factory fromIndicator(ViaRewindConfig.CooldownIndicator indicator) {
        switch (indicator) {
            case TITLE: {
                return TitleCooldownVisualization::new;
            }
            case BOSS_BAR: {
                return BossBarVisualization::new;
            }
            case ACTION_BAR: {
                return ActionBarVisualization::new;
            }
            case DISABLED: {
                return DISABLED;
            }
        }
        throw new IllegalArgumentException("Unexpected: " + (Object)((Object)indicator));
    }
}
