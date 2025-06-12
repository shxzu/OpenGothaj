// ERROR: Unable to apply inner class name fixup
package net.minecraft.block;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

class BlockBeacon.1
implements Runnable {
    private final World val$worldIn;
    private final BlockPos val$blockpos;

    BlockBeacon.1(World world, BlockPos blockPos) {
        this.val$worldIn = world;
        this.val$blockpos = blockPos;
    }

    @Override
    public void run() {
        TileEntity tileentity = this.val$worldIn.getTileEntity(this.val$blockpos);
        if (tileentity instanceof TileEntityBeacon) {
            ((TileEntityBeacon)tileentity).updateBeacon();
            this.val$worldIn.addBlockEvent(this.val$blockpos, Blocks.beacon, 1, 0);
        }
    }
}
