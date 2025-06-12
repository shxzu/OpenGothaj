package net.minecraft.client.gui.achievement;

import java.util.Comparator;
import net.minecraft.item.Item;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatList;

class GuiStats$StatsBlock$1
implements Comparator<StatCrafting> {
    GuiStats$StatsBlock$1() {
    }

    @Override
    public int compare(StatCrafting p_compare_1_, StatCrafting p_compare_2_) {
        int j = Item.getIdFromItem(p_compare_1_.func_150959_a());
        int k = Item.getIdFromItem(p_compare_2_.func_150959_a());
        StatBase statbase = null;
        StatBase statbase1 = null;
        if (StatsBlock.this.field_148217_o == 2) {
            statbase = StatList.mineBlockStatArray[j];
            statbase1 = StatList.mineBlockStatArray[k];
        } else if (StatsBlock.this.field_148217_o == 0) {
            statbase = StatList.objectCraftStats[j];
            statbase1 = StatList.objectCraftStats[k];
        } else if (StatsBlock.this.field_148217_o == 1) {
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
            int l = StatsBlock.this.this$0.field_146546_t.readStat(statbase);
            if (l != (i1 = StatsBlock.this.this$0.field_146546_t.readStat(statbase1))) {
                return (l - i1) * StatsBlock.this.field_148215_p;
            }
        }
        return j - k;
    }
}
