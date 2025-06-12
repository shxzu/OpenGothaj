package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;

class BlockModelShapes$7
extends StateMapperBase {
    BlockModelShapes$7() {
    }

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
        LinkedHashMap<IProperty, Comparable> map = Maps.newLinkedHashMap(state.getProperties());
        String s = BlockStoneSlabNew.VARIANT.getName((BlockStoneSlabNew.EnumType)map.remove(BlockStoneSlabNew.VARIANT));
        map.remove(BlockStoneSlab.SEAMLESS);
        String s1 = state.getValue(BlockStoneSlabNew.SEAMLESS) != false ? "all" : "normal";
        return new ModelResourceLocation(String.valueOf(s) + "_double_slab", s1);
    }
}
