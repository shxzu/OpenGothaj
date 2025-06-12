package xyz.cucumber.base.module.feat.player;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventBlockBreak;
import xyz.cucumber.base.events.ext.EventClick;
import xyz.cucumber.base.events.ext.EventSendPacket;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.utils.Timer;

@ModuleInfo(category=Category.PLAYER, description="Automatically switch to best tool", name="Auto Tool")
public class AutoToolModule
extends Mod {
    public int serverSideSlot = -1;
    public int lastServerSideSlot = -1;
    private int tool = -1;
    private boolean reset;
    private Timer timer = new Timer();

    @EventListener
    public void onBlockBreak(EventBlockBreak e) {
        int slot = this.findTool(e.getLocation());
        if (slot != -1) {
            this.tool = slot;
        }
    }

    @EventListener
    public void onSendPacket(EventSendPacket e) {
        Packet p = e.getPacket();
        if (p instanceof C08PacketPlayerBlockPlacement) {
            this.serverSideSlot = this.mc.thePlayer.inventory.currentItem;
        }
    }

    @EventListener
    public void onLegitPlace(EventClick e) {
        if (this.mc.gameSettings.keyBindAttack.isKeyDown()) {
            int cfr_ignored_0 = this.mc.thePlayer.inventory.currentItem;
        }
        if (!this.mc.gameSettings.keyBindAttack.isKeyDown() && this.timer.hasTimeElapsed(60.0, false) && this.serverSideSlot != this.mc.thePlayer.inventory.currentItem) {
            this.serverSideSlot = this.mc.thePlayer.inventory.currentItem;
            this.reset = false;
            this.tool = -1;
            this.timer.reset();
            return;
        }
        if (this.tool != -1) {
            if (!this.reset) {
                this.timer.reset();
                this.reset = true;
            }
            if (this.timer.hasTimeElapsed(0.0, false)) {
                if (this.mc.thePlayer.inventory.currentItem != this.tool) {
                    this.mc.thePlayer.inventory.currentItem = this.tool;
                }
                this.reset = false;
                this.tool = -1;
            }
        }
    }

    private int findTool(BlockPos blockPos) {
        float bestSpeed = 1.0f;
        int bestSlot = -1;
        IBlockState blockState = this.mc.theWorld.getBlockState(blockPos);
        int i = 0;
        while (i < 9) {
            float speed;
            ItemStack itemStack = this.mc.thePlayer.inventory.getStackInSlot(i);
            if (itemStack != null && (speed = itemStack.getStrVsBlock(blockState.getBlock())) > bestSpeed) {
                bestSpeed = speed;
                bestSlot = i;
            }
            ++i;
        }
        return bestSlot;
    }

    public ItemStack getCurrentItemInSlot(int slot) {
        return slot < 9 && slot >= 0 ? this.mc.thePlayer.inventory.mainInventory[slot] : null;
    }

    public float getStrVsBlock(Block blockIn, int slot) {
        float f = 1.0f;
        if (this.mc.thePlayer.inventory.mainInventory[slot] != null) {
            f *= this.mc.thePlayer.inventory.mainInventory[slot].getStrVsBlock(blockIn);
        }
        return f;
    }

    public float getPlayerRelativeBlockHardness(BlockPos pos, int slot) {
        Block block = this.mc.theWorld.getBlockState(pos).getBlock();
        float f = block.getBlockHardness(this.mc.theWorld, pos);
        return f < 0.0f ? 0.0f : (!this.canHeldItemHarvest(block, slot) ? this.getToolDigEfficiency(block, slot) / f / 100.0f : this.getToolDigEfficiency(block, slot) / f / 30.0f);
    }

    public boolean canHeldItemHarvest(Block blockIn, int slot) {
        if (blockIn.getMaterial().isToolNotRequired()) {
            return true;
        }
        ItemStack itemstack = this.mc.thePlayer.inventory.getStackInSlot(slot);
        return itemstack != null && itemstack.canHarvestBlock(blockIn);
    }

    public float getToolDigEfficiency(Block blockIn, int slot) {
        float f = this.getStrVsBlock(blockIn, slot);
        if (f > 1.0f) {
            int i = EnchantmentHelper.getEfficiencyModifier(this.mc.thePlayer);
            ItemStack itemstack = this.getCurrentItemInSlot(slot);
            if (i > 0 && itemstack != null) {
                f += (float)(i * i + 1);
            }
        }
        if (this.mc.thePlayer.isPotionActive(Potion.digSpeed)) {
            f *= 1.0f + (float)(this.mc.thePlayer.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2f;
        }
        if (this.mc.thePlayer.isPotionActive(Potion.digSlowdown)) {
            float f1;
            switch (this.mc.thePlayer.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
                case 0: {
                    f1 = 0.3f;
                    break;
                }
                case 1: {
                    f1 = 0.09f;
                    break;
                }
                case 2: {
                    f1 = 0.0027f;
                    break;
                }
                default: {
                    f1 = 8.1E-4f;
                }
            }
            f *= f1;
        }
        if (this.mc.thePlayer.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this.mc.thePlayer)) {
            f /= 5.0f;
        }
        if (!this.mc.thePlayer.onGround) {
            f /= 5.0f;
        }
        return f;
    }
}
