package net.minecraft.client.renderer;

import net.minecraft.block.BlockQuartz;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;

class BlockModelShapes$1
extends StateMapperBase {
    BlockModelShapes$1() {
    }

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
        BlockQuartz.EnumType blockquartz$enumtype = state.getValue(BlockQuartz.VARIANT);
        switch (blockquartz$enumtype) {
            default: {
                return new ModelResourceLocation("quartz_block", "normal");
            }
            case CHISELED: {
                return new ModelResourceLocation("chiseled_quartz_block", "normal");
            }
            case LINES_Y: {
                return new ModelResourceLocation("quartz_column", "axis=y");
            }
            case LINES_X: {
                return new ModelResourceLocation("quartz_column", "axis=x");
            }
            case LINES_Z: 
        }
        return new ModelResourceLocation("quartz_column", "axis=z");
    }
}
