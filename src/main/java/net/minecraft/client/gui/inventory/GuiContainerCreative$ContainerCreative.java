package net.minecraft.client.gui.inventory;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class GuiContainerCreative$ContainerCreative
extends Container {
    public List<ItemStack> itemList = Lists.newArrayList();

    public GuiContainerCreative$ContainerCreative(EntityPlayer p_i1086_1_) {
        InventoryPlayer inventoryplayer = p_i1086_1_.inventory;
        int i = 0;
        while (i < 5) {
            int j = 0;
            while (j < 9) {
                this.addSlotToContainer(new Slot(field_147060_v, i * 9 + j, 9 + j * 18, 18 + i * 18));
                ++j;
            }
            ++i;
        }
        int k = 0;
        while (k < 9) {
            this.addSlotToContainer(new Slot(inventoryplayer, k, 9 + k * 18, 112));
            ++k;
        }
        this.scrollTo(0.0f);
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    public void scrollTo(float p_148329_1_) {
        int i = (this.itemList.size() + 9 - 1) / 9 - 5;
        int j = (int)((double)(p_148329_1_ * (float)i) + 0.5);
        if (j < 0) {
            j = 0;
        }
        int k = 0;
        while (k < 5) {
            int l = 0;
            while (l < 9) {
                int i1 = l + (k + j) * 9;
                if (i1 >= 0 && i1 < this.itemList.size()) {
                    field_147060_v.setInventorySlotContents(l + k * 9, this.itemList.get(i1));
                } else {
                    field_147060_v.setInventorySlotContents(l + k * 9, null);
                }
                ++l;
            }
            ++k;
        }
    }

    public boolean func_148328_e() {
        return this.itemList.size() > 45;
    }

    @Override
    protected void retrySlotClick(int slotId, int clickedButton, boolean mode, EntityPlayer playerIn) {
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        Slot slot;
        if (index >= this.inventorySlots.size() - 9 && index < this.inventorySlots.size() && (slot = (Slot)this.inventorySlots.get(index)) != null && slot.getHasStack()) {
            slot.putStack(null);
        }
        return null;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return slotIn.yDisplayPosition > 90;
    }

    @Override
    public boolean canDragIntoSlot(Slot p_94531_1_) {
        return p_94531_1_.inventory instanceof InventoryPlayer || p_94531_1_.yDisplayPosition > 90 && p_94531_1_.xDisplayPosition <= 162;
    }
}
