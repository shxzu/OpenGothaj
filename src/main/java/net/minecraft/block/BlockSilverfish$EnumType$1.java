package net.minecraft.block;

import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

class BlockSilverfish$EnumType$1
extends BlockSilverfish.EnumType {
    BlockSilverfish$EnumType$1(int $anonymous0, String $anonymous1) {
    }

    @Override
    public IBlockState getModelBlock() {
        return Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE);
    }
}
