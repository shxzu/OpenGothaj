package net.minecraft.block;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class BlockJukebox$TileEntityJukebox
extends TileEntity {
    private ItemStack record;

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("RecordItem", 10)) {
            this.setRecord(ItemStack.loadItemStackFromNBT(compound.getCompoundTag("RecordItem")));
        } else if (compound.getInteger("Record") > 0) {
            this.setRecord(new ItemStack(Item.getItemById(compound.getInteger("Record")), 1, 0));
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        if (this.getRecord() != null) {
            compound.setTag("RecordItem", this.getRecord().writeToNBT(new NBTTagCompound()));
        }
    }

    public ItemStack getRecord() {
        return this.record;
    }

    public void setRecord(ItemStack recordStack) {
        this.record = recordStack;
        this.markDirty();
    }
}
