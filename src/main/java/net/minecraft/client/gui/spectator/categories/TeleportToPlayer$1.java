package net.minecraft.client.gui.spectator.categories;

import com.google.common.collect.ComparisonChain;
import java.util.Comparator;
import net.minecraft.client.network.NetworkPlayerInfo;

class TeleportToPlayer$1
implements Comparator<NetworkPlayerInfo> {
    TeleportToPlayer$1() {
    }

    @Override
    public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_) {
        return ComparisonChain.start().compare(p_compare_1_.getGameProfile().getId(), p_compare_2_.getGameProfile().getId()).result();
    }
}
