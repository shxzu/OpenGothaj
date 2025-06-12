package net.minecraft.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class BlockAnvil$Anvil
implements IInteractionObject {
    private final World world;
    private final BlockPos position;

    public BlockAnvil$Anvil(World worldIn, BlockPos pos) {
        this.world = worldIn;
        this.position = pos;
    }

    @Override
    public String getName() {
        return "anvil";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public IChatComponent getDisplayName() {
        return new ChatComponentTranslation(String.valueOf(Blocks.anvil.getUnlocalizedName()) + ".name", new Object[0]);
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new ContainerRepair(playerInventory, this.world, this.position, playerIn);
    }

    @Override
    public String getGuiID() {
        return "minecraft:anvil";
    }
}
