package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

class BlockBeacon$1
implements Runnable {
    private final World val$worldIn;
    private final BlockPos val$glassPos;

    BlockBeacon$1(World world, BlockPos blockPos) {
        this.val$worldIn = world;
        this.val$glassPos = blockPos;
    }

    @Override
    public void run() {
        Chunk chunk = this.val$worldIn.getChunkFromBlockCoords(this.val$glassPos);
        int i = this.val$glassPos.getY() - 1;
        while (i >= 0) {
            final BlockPos blockpos = new BlockPos(this.val$glassPos.getX(), i, this.val$glassPos.getZ());
            if (!chunk.canSeeSky(blockpos)) break;
            IBlockState iblockstate = this.val$worldIn.getBlockState(blockpos);
            if (iblockstate.getBlock() == Blocks.beacon) {
                ((WorldServer)this.val$worldIn).addScheduledTask(new Runnable(){

                    @Override
                    public void run() {
                        TileEntity tileentity = val$worldIn.getTileEntity(blockpos);
                        if (tileentity instanceof TileEntityBeacon) {
                            ((TileEntityBeacon)tileentity).updateBeacon();
                            val$worldIn.addBlockEvent(blockpos, Blocks.beacon, 1, 0);
                        }
                    }
                });
            }
            --i;
        }
    }
}
