package net.minecraft.client.gui.achievement;

import java.util.Comparator;
import net.minecraft.item.Item;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatList;

class GuiStats$StatsItem$1
implements Comparator<StatCrafting> {
    GuiStats$StatsItem$1() {
    }

    @Override
    public int compare(StatCrafting p_compare_1_, StatCrafting p_compare_2_) {
        int j = Item.getIdFromItem(p_compare_1_.func_150959_a());
        int k = Item.getIdFromItem(p_compare_2_.func_150959_a());
        StatBase statbase = null;
        StatBase statbase1 = null;
        if (StatsItem.this.field_148217_o == 0) {
            statbase = StatList.objectBreakStats[j];
            statbase1 = StatList.objectBreakStats[k];
        } else if (StatsItem.this.field_148217_o == 1) {
            statbase = StatList.objectCraftStats[j];
            statbase1 = StatList.objectCraftStats[k];
        } else if (StatsItem.this.field_148217_o == 2) {
            statbase = StatList.objectUseStats[j];
            statbase1 = StatList.objectUseStats[k];
        }
        if (statbase != null || statbase1 != null) {
            int i1;
            if (statbase == null) {
                return 1;
            }
            if (statbase1 == null) {
                return -1;
            }
            int l = StatsItem.this.this$0.field_146546_t.readStat(statbase);
            if (l != (i1 = StatsItem.this.this$0.field_146546_t.readStat(statbase1))) {
                return (l - i1) * StatsItem.this.field_148215_p;
            }
        }
        return j - k;
    }
}
