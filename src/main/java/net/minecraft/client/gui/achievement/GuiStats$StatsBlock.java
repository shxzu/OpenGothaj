package net.minecraft.client.gui.achievement;

import com.google.common.collect.Lists;
import java.util.Comparator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatCrafting;
import net.minecraft.stats.StatList;

class GuiStats$StatsBlock
extends GuiStats.Stats {
    public GuiStats$StatsBlock(Minecraft mcIn) {
        super(GuiStats.this, mcIn);
        this.statsHolder = Lists.newArrayList();
        for (StatCrafting statcrafting : StatList.objectMineStats) {
            boolean flag = false;
            int i = Item.getIdFromItem(statcrafting.func_150959_a());
            if (GuiStats.this.field_146546_t.readStat(statcrafting) > 0) {
                flag = true;
            } else if (StatList.objectUseStats[i] != null && GuiStats.this.field_146546_t.readStat(StatList.objectUseStats[i]) > 0) {
                flag = true;
            } else if (StatList.objectCraftStats[i] != null && GuiStats.this.field_146546_t.readStat(StatList.objectCraftStats[i]) > 0) {
                flag = true;
            }
            if (!flag) continue;
            this.statsHolder.add(statcrafting);
        }
        this.statSorter = new Comparator<StatCrafting>(){

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
                    int l = GuiStats.this.field_146546_t.readStat(statbase);
                    if (l != (i1 = GuiStats.this.field_146546_t.readStat(statbase1))) {
                        return (l - i1) * StatsBlock.this.field_148215_p;
                    }
                }
                return j - k;
            }
        };
    }

    @Override
    protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {
        super.drawListHeader(p_148129_1_, p_148129_2_, p_148129_3_);
        if (this.field_148218_l == 0) {
            GuiStats.this.drawSprite(p_148129_1_ + 115 - 18 + 1, p_148129_2_ + 1 + 1, 18, 18);
        } else {
            GuiStats.this.drawSprite(p_148129_1_ + 115 - 18, p_148129_2_ + 1, 18, 18);
        }
        if (this.field_148218_l == 1) {
            GuiStats.this.drawSprite(p_148129_1_ + 165 - 18 + 1, p_148129_2_ + 1 + 1, 36, 18);
        } else {
            GuiStats.this.drawSprite(p_148129_1_ + 165 - 18, p_148129_2_ + 1, 36, 18);
        }
        if (this.field_148218_l == 2) {
            GuiStats.this.drawSprite(p_148129_1_ + 215 - 18 + 1, p_148129_2_ + 1 + 1, 54, 18);
        } else {
            GuiStats.this.drawSprite(p_148129_1_ + 215 - 18, p_148129_2_ + 1, 54, 18);
        }
    }

    @Override
    protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
        StatCrafting statcrafting = this.func_148211_c(entryID);
        Item item = statcrafting.func_150959_a();
        GuiStats.this.drawStatsScreen(p_180791_2_ + 40, p_180791_3_, item);
        int i = Item.getIdFromItem(item);
        this.func_148209_a(StatList.objectCraftStats[i], p_180791_2_ + 115, p_180791_3_, entryID % 2 == 0);
        this.func_148209_a(StatList.objectUseStats[i], p_180791_2_ + 165, p_180791_3_, entryID % 2 == 0);
        this.func_148209_a(statcrafting, p_180791_2_ + 215, p_180791_3_, entryID % 2 == 0);
    }

    @Override
    protected String func_148210_b(int p_148210_1_) {
        return p_148210_1_ == 0 ? "stat.crafted" : (p_148210_1_ == 1 ? "stat.used" : "stat.mined");
    }
}
