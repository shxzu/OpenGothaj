package net.minecraft.client.gui.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

class GuiContainerCreative$CreativeSlot
extends Slot {
    private final Slot slot;

    public GuiContainerCreative$CreativeSlot(Slot p_i46313_2_, int p_i46313_3_) {
        super(p_i46313_2_.inventory, p_i46313_3_, 0, 0);
        this.slot = p_i46313_2_;
    }

    @Override
    public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack) {
        this.slot.onPickupFromSlot(playerIn, stack);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return this.slot.isItemValid(stack);
    }

    @Override
    public ItemStack getStack() {
        return this.slot.getStack();
    }

    @Override
    public boolean getHasStack() {
        return this.slot.getHasStack();
    }

    @Override
    public void putStack(ItemStack stack) {
        this.slot.putStack(stack);
    }

    @Override
    public void onSlotChanged() {
        this.slot.onSlotChanged();
    }

    @Override
    public int getSlotStackLimit() {
        return this.slot.getSlotStackLimit();
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return this.slot.getItemStackLimit(stack);
    }

    @Override
    public String getSlotTexture() {
        return this.slot.getSlotTexture();
    }

    @Override
    public ItemStack decrStackSize(int amount) {
        return this.slot.decrStackSize(amount);
    }

    @Override
    public boolean isHere(IInventory inv, int slotIn) {
        return this.slot.isHere(inv, slotIn);
    }

    static Slot access$0(GuiContainerCreative$CreativeSlot creativeSlot) {
        return creativeSlot.slot;
    }
}
