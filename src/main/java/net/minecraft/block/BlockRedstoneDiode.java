package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockRedstoneDiode
extends BlockDirectional {
    protected final boolean isRepeaterPowered;

    protected BlockRedstoneDiode(boolean powered) {
        super(Material.circuits);
        this.isRepeaterPowered = powered;
        this.setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 0.125f, 1.0f);
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return World.doesBlockHaveSolidTopSurface(worldIn, pos.down()) ? super.canPlaceBlockAt(worldIn, pos) : false;
    }

    public boolean canBlockStay(World worldIn, BlockPos pos) {
        return World.doesBlockHaveSolidTopSurface(worldIn, pos.down());
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!this.isLocked(worldIn, pos, state)) {
            boolean flag = this.shouldBePowered(worldIn, pos, state);
            if (this.isRepeaterPowered && !flag) {
                worldIn.setBlockState(pos, this.getUnpoweredState(state), 2);
            } else if (!this.isRepeaterPowered) {
                worldIn.setBlockState(pos, this.getPoweredState(state), 2);
                if (!flag) {
                    worldIn.updateBlockTick(pos, this.getPoweredState(state).getBlock(), this.getTickDelay(state), -1);
                }
            }
        }
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return side.getAxis() != EnumFacing.Axis.Y;
    }

    protected boolean isPowered(IBlockState state) {
        return this.isRepeaterPowered;
    }

    @Override
    public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
        return this.getWeakPower(worldIn, pos, state, side);
    }

    @Override
    public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
        return !this.isPowered(state) ? 0 : (state.getValue(FACING) == side ? this.getActiveSignal(worldIn, pos, state) : 0);
    }

    @Override
    public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
        if (this.canBlockStay(worldIn, pos)) {
            this.updateState(worldIn, pos, state);
        } else {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
            EnumFacing[] enumFacingArray = EnumFacing.values();
            int n = enumFacingArray.length;
            int n2 = 0;
            while (n2 < n) {
                EnumFacing enumfacing = enumFacingArray[n2];
                worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
                ++n2;
            }
        }
    }

    protected void updateState(World worldIn, BlockPos pos, IBlockState state) {
        if (!this.isLocked(worldIn, pos, state)) {
            boolean flag = this.shouldBePowered(worldIn, pos, state);
            if ((this.isRepeaterPowered && !flag || !this.isRepeaterPowered && flag) && !worldIn.isBlockTickPending(pos, this)) {
                int i = -1;
                if (this.isFacingTowardsRepeater(worldIn, pos, state)) {
                    i = -3;
                } else if (this.isRepeaterPowered) {
                    i = -2;
                }
                worldIn.updateBlockTick(pos, this, this.getDelay(state), i);
            }
        }
    }

    public boolean isLocked(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
        return false;
    }

    protected boolean shouldBePowered(World worldIn, BlockPos pos, IBlockState state) {
        return this.calculateInputStrength(worldIn, pos, state) > 0;
    }

    protected int calculateInputStrength(World worldIn, BlockPos pos, IBlockState state) {
        EnumFacing enumfacing = state.getValue(FACING);
        BlockPos blockpos = pos.offset(enumfacing);
        int i = worldIn.getRedstonePower(blockpos, enumfacing);
        if (i >= 15) {
            return i;
        }
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        return Math.max(i, iblockstate.getBlock() == Blocks.redstone_wire ? iblockstate.getValue(BlockRedstoneWire.POWER) : 0);
    }

    protected int getPowerOnSides(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
        EnumFacing enumfacing = state.getValue(FACING);
        EnumFacing enumfacing1 = enumfacing.rotateY();
        EnumFacing enumfacing2 = enumfacing.rotateYCCW();
        return Math.max(this.getPowerOnSide(worldIn, pos.offset(enumfacing1), enumfacing1), this.getPowerOnSide(worldIn, pos.offset(enumfacing2), enumfacing2));
    }

    protected int getPowerOnSide(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
        return this.canPowerSide(block) ? (block == Blocks.redstone_wire ? iblockstate.getValue(BlockRedstoneWire.POWER).intValue() : worldIn.getStrongPower(pos, side)) : 0;
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (this.shouldBePowered(worldIn, pos, state)) {
            worldIn.scheduleUpdate(pos, this, 1);
        }
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.notifyNeighbors(worldIn, pos, state);
    }

    protected void notifyNeighbors(World worldIn, BlockPos pos, IBlockState state) {
        EnumFacing enumfacing = state.getValue(FACING);
        BlockPos blockpos = pos.offset(enumfacing.getOpposite());
        worldIn.notifyBlockOfStateChange(blockpos, this);
        worldIn.notifyNeighborsOfStateExcept(blockpos, this, enumfacing);
    }

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        if (this.isRepeaterPowered) {
            EnumFacing[] enumFacingArray = EnumFacing.values();
            int n = enumFacingArray.length;
            int n2 = 0;
            while (n2 < n) {
                EnumFacing enumfacing = enumFacingArray[n2];
                worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this);
                ++n2;
            }
        }
        super.onBlockDestroyedByPlayer(worldIn, pos, state);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    protected boolean canPowerSide(Block blockIn) {
        return blockIn.canProvidePower();
    }

    protected int getActiveSignal(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
        return 15;
    }

    public static boolean isRedstoneRepeaterBlockID(Block blockIn) {
        return Blocks.unpowered_repeater.isAssociated(blockIn) || Blocks.unpowered_comparator.isAssociated(blockIn);
    }

    public boolean isAssociated(Block other) {
        return other == this.getPoweredState(this.getDefaultState()).getBlock() || other == this.getUnpoweredState(this.getDefaultState()).getBlock();
    }

    public boolean isFacingTowardsRepeater(World worldIn, BlockPos pos, IBlockState state) {
        EnumFacing enumfacing = state.getValue(FACING).getOpposite();
        BlockPos blockpos = pos.offset(enumfacing);
        return BlockRedstoneDiode.isRedstoneRepeaterBlockID(worldIn.getBlockState(blockpos).getBlock()) ? worldIn.getBlockState(blockpos).getValue(FACING) != enumfacing : false;
    }

    protected int getTickDelay(IBlockState state) {
        return this.getDelay(state);
    }

    protected abstract int getDelay(IBlockState var1);

    protected abstract IBlockState getPoweredState(IBlockState var1);

    protected abstract IBlockState getUnpoweredState(IBlockState var1);

    @Override
    public boolean isAssociatedBlock(Block other) {
        return this.isAssociated(other);
    }

    @Override
    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }
}
