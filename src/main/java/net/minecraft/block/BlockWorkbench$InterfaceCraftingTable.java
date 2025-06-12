package net.minecraft.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class BlockWorkbench$InterfaceCraftingTable
implements IInteractionObject {
    private final World world;
    private final BlockPos position;

    public BlockWorkbench$InterfaceCraftingTable(World worldIn, BlockPos pos) {
        this.world = worldIn;
        this.position = pos;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentTranslation(String.valueOf(Blocks.crafting_table.getUnlocalizedName()) + ".name", new Object[0]);
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerWorkbench(playerInventory, this.world, this.position);
    }

    @Override
    public String getGuiID() {
        return "minecraft:crafting_table";
    }
}
