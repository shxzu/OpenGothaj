package xyz.cucumber.base.module.feat.player;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventAttack;
import xyz.cucumber.base.events.ext.EventGameLoop;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.feat.combat.KillAuraModule;
import xyz.cucumber.base.module.feat.other.TeamsModule;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.game.EntityUtils;
import xyz.cucumber.base.utils.game.InventoryUtils;

@ModuleInfo(category=Category.PLAYER, description="Automatically switch to best weapon", name="Auto Weapon")
public class AutoWeaponModule
extends Mod {
    public Timer timer = new Timer();

    @EventListener
    public void onGameLoop(EventGameLoop e) {
        EntityLivingBase entity = null;
        KillAuraModule killAura = (KillAuraModule)Client.INSTANCE.getModuleManager().getModule(KillAuraModule.class);
        if (killAura.isEnabled()) {
            entity = EntityUtils.getTarget(killAura.Range.getValue() + 0.1, killAura.Targets.getMode(), "Single", (int)killAura.switchDelay.getValue(), Client.INSTANCE.getModuleManager().getModule(TeamsModule.class).isEnabled(), killAura.TroughWalls.isEnabled(), killAura.attackInvisible.isEnabled(), killAura.attackDead.isEnabled());
        }
        if (entity != null) {
            int i = 36;
            while (i < 45) {
                if (this.mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null && this.mc.thePlayer.inventoryContainer.getSlot(i).getStack() == this.bestWeapon() && this.mc.thePlayer.inventory.currentItem != i - 36) {
                    this.mc.thePlayer.inventory.currentItem = i - 36;
                    InventoryUtils.timer.reset();
                    break;
                }
                ++i;
            }
        }
    }

    @EventListener
    public void onAttack(EventAttack e) {
        int i = 36;
        while (i < 45) {
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null && this.mc.thePlayer.inventoryContainer.getSlot(i).getStack() == this.bestWeapon() && this.mc.thePlayer.inventory.currentItem != i - 36) {
                this.mc.thePlayer.inventory.currentItem = i - 36;
                InventoryUtils.timer.reset();
                break;
            }
            ++i;
        }
    }

    public ItemStack bestWeapon() {
        ItemStack bestWeapon = null;
        float itemDamage = -1.0f;
        int i = 36;
        while (i < 45) {
            float toolDamage;
            ItemStack is;
            if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && ((is = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack()).getItem() instanceof ItemSword || is.getItem() instanceof ItemAxe || is.getItem() instanceof ItemPickaxe) && (toolDamage = this.getItemDamage(is)) >= itemDamage) {
                itemDamage = this.getItemDamage(is);
                bestWeapon = is;
            }
            ++i;
        }
        return bestWeapon;
    }

    public float getItemDamage(ItemStack itemStack) {
        float damage = InventoryUtils.getToolMaterialRating(itemStack, true);
        damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25f;
        damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, itemStack) * 0.5f;
        damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, itemStack) * 0.01f;
        damage += (float)(itemStack.getMaxDamage() - itemStack.getItemDamage()) * 1.0E-12f;
        if (itemStack.getItem() instanceof ItemSword) {
            damage = (float)((double)damage + 0.2);
        }
        return damage;
    }
}
