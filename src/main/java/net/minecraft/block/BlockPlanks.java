package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class BlockPlanks
extends Block {
    public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);

    public BlockPlanks() {
        super(Material.wood);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.OAK));
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANT).getMetadata();
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        EnumType[] enumTypeArray = EnumType.values();
        int n = enumTypeArray.length;
        int n2 = 0;
        while (n2 < n) {
            EnumType blockplanks$enumtype = enumTypeArray[n2];
            list.add(new ItemStack(itemIn, 1, blockplanks$enumtype.getMetadata()));
            ++n2;
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, EnumType.byMetadata(meta));
    }

    @Override
    public MapColor getMapColor(IBlockState state) {
        return state.getValue(VARIANT).getMapColor();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT).getMetadata();
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, VARIANT);
    }

    public static enum EnumType implements IStringSerializable
    {
        OAK(0, "oak", MapColor.woodColor),
        SPRUCE(1, "spruce", MapColor.obsidianColor),
        BIRCH(2, "birch", MapColor.sandColor),
        JUNGLE(3, "jungle", MapColor.dirtColor),
        ACACIA(4, "acacia", MapColor.adobeColor),
        DARK_OAK(5, "dark_oak", "big_oak", MapColor.brownColor);

        private static final EnumType[] META_LOOKUP;
        private final int meta;
        private final String name;
        private final String unlocalizedName;
        private final MapColor mapColor;

        static {
            META_LOOKUP = new EnumType[EnumType.values().length];
            EnumType[] enumTypeArray = EnumType.values();
            int n = enumTypeArray.length;
            int n2 = 0;
            while (n2 < n) {
                EnumType blockplanks$enumtype;
                EnumType.META_LOOKUP[blockplanks$enumtype.getMetadata()] = blockplanks$enumtype = enumTypeArray[n2];
                ++n2;
            }
        }

        private EnumType(int p_i46388_3_, String p_i46388_4_, MapColor p_i46388_5_) {
            this(p_i46388_3_, p_i46388_4_, p_i46388_4_, p_i46388_5_);
        }

        private EnumType(int p_i46389_3_, String p_i46389_4_, String p_i46389_5_, MapColor p_i46389_6_) {
            this.meta = p_i46389_3_;
            this.name = p_i46389_4_;
            this.unlocalizedName = p_i46389_5_;
            this.mapColor = p_i46389_6_;
        }

        public int getMetadata() {
            return this.meta;
        }

        public MapColor getMapColor() {
            return this.mapColor;
        }

        public String toString() {
            return this.name;
        }

        public static EnumType byMetadata(int meta) {
            if (meta < 0 || meta >= META_LOOKUP.length) {
                meta = 0;
            }
            return META_LOOKUP[meta];
        }

        @Override
        public String getName() {
            return this.name;
        }

        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }
    }
}
