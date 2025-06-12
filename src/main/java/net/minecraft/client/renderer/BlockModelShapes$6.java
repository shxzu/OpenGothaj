package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;

class BlockModelShapes$6
extends StateMapperBase {
    BlockModelShapes$6() {
    }

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
        LinkedHashMap<IProperty, Comparable> map = Maps.newLinkedHashMap(state.getProperties());
        String s = BlockStoneSlab.VARIANT.getName((BlockStoneSlab.EnumType)map.remove(BlockStoneSlab.VARIANT));
        map.remove(BlockStoneSlab.SEAMLESS);
        String s1 = state.getValue(BlockStoneSlab.SEAMLESS) != false ? "all" : "normal";
        return new ModelResourceLocation(String.valueOf(s) + "_double_slab", s1);
    }
}
