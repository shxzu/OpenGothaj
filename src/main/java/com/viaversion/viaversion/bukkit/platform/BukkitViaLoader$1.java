package com.viaversion.viaversion.bukkit.platform;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.bukkit.listeners.protocol1_9to1_8.HandItemCache;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.HandItemProvider;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

class BukkitViaLoader$1
extends HandItemProvider {
    BukkitViaLoader$1() {
    }

    @Override
    public Item getHandItem(UserConnection info) {
        if (BukkitViaLoader.this.handItemCache != null) {
            return BukkitViaLoader.this.handItemCache.getHandItem(info.getProtocolInfo().getUuid());
        }
        try {
            return (Item)Bukkit.getScheduler().callSyncMethod(Bukkit.getPluginManager().getPlugin("ViaVersion"), () -> {
                UUID playerUUID = info.getProtocolInfo().getUuid();
                Player player = Bukkit.getPlayer((UUID)playerUUID);
                if (player != null) {
                    return HandItemCache.convert(player.getItemInHand());
                }
                return null;
            }).get(10L, TimeUnit.SECONDS);
        }
        catch (Exception e) {
            Via.getPlatform().getLogger().log(Level.SEVERE, "Error fetching hand item", e);
            return null;
        }
    }
}
