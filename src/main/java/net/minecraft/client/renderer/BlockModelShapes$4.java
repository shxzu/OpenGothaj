package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStem;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;

class BlockModelShapes$4
extends StateMapperBase {
    BlockModelShapes$4() {
    }

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
        LinkedHashMap<IProperty, Comparable> map = Maps.newLinkedHashMap(state.getProperties());
        if (state.getValue(BlockStem.FACING) != EnumFacing.UP) {
            map.remove(BlockStem.AGE);
        }
        return new ModelResourceLocation((ResourceLocation)Block.blockRegistry.getNameForObject(state.getBlock()), this.getPropertyString(map));
    }
}
