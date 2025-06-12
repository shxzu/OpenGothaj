package net.minecraft.client.gui;

import com.google.common.collect.ComparisonChain;
import java.util.Comparator;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.world.WorldSettings;

class GuiPlayerTabOverlay$PlayerComparator
implements Comparator<NetworkPlayerInfo> {
    private GuiPlayerTabOverlay$PlayerComparator() {
    }

    @Override
    public int compare(NetworkPlayerInfo p_compare_1_, NetworkPlayerInfo p_compare_2_) {
        ScorePlayerTeam scoreplayerteam = p_compare_1_.getPlayerTeam();
        ScorePlayerTeam scoreplayerteam1 = p_compare_2_.getPlayerTeam();
        return ComparisonChain.start().compareTrueFirst(p_compare_1_.getGameType() != WorldSettings.GameType.SPECTATOR, p_compare_2_.getGameType() != WorldSettings.GameType.SPECTATOR).compare((Comparable<?>)((Object)(scoreplayerteam != null ? scoreplayerteam.getRegisteredName() : "")), (Comparable<?>)((Object)(scoreplayerteam1 != null ? scoreplayerteam1.getRegisteredName() : ""))).compare((Comparable<?>)((Object)p_compare_1_.getGameProfile().getName()), (Comparable<?>)((Object)p_compare_2_.getGameProfile().getName())).result();
    }
}
