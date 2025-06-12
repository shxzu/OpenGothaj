package net.minecraft.block;

import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

class BlockSilverfish$EnumType$6
extends BlockSilverfish.EnumType {
    BlockSilverfish$EnumType$6(int $anonymous0, String $anonymous1, String $anonymous2) {
    }

    @Override
    public IBlockState getModelBlock() {
        return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED);
    }
}
