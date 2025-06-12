package net.minecraft.client.renderer;

import com.google.common.collect.Maps;
import java.util.LinkedHashMap;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.resources.model.ModelResourceLocation;

class BlockModelShapes$5
extends StateMapperBase {
    BlockModelShapes$5() {
    }

    @Override
    protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
        LinkedHashMap<IProperty, Comparable> map = Maps.newLinkedHashMap(state.getProperties());
        String s = BlockDirt.VARIANT.getName((BlockDirt.DirtType)map.remove(BlockDirt.VARIANT));
        if (BlockDirt.DirtType.PODZOL != state.getValue(BlockDirt.VARIANT)) {
            map.remove(BlockDirt.SNOWY);
        }
        return new ModelResourceLocation(s, this.getPropertyString(map));
    }
}
