package net.minecraft.block;

import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.IStringSerializable;

public enum BlockSilverfish$EnumType implements IStringSerializable
{
    STONE(0, "stone"){

        @Override
        public IBlockState getModelBlock() {
            return Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE);
        }
    }
    ,
    COBBLESTONE(1, "cobblestone", "cobble"){

        @Override
        public IBlockState getModelBlock() {
            return Blocks.cobblestone.getDefaultState();
        }
    }
    ,
    STONEBRICK(2, "stone_brick", "brick"){

        @Override
        public IBlockState getModelBlock() {
            return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.DEFAULT);
        }
    }
    ,
    MOSSY_STONEBRICK(3, "mossy_brick", "mossybrick"){

        @Override
        public IBlockState getModelBlock() {
            return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY);
        }
    }
    ,
    CRACKED_STONEBRICK(4, "cracked_brick", "crackedbrick"){

        @Override
        public IBlockState getModelBlock() {
            return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED);
        }
    }
    ,
    CHISELED_STONEBRICK(5, "chiseled_brick", "chiseledbrick"){

        @Override
        public IBlockState getModelBlock() {
            return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED);
        }
    };

    private static final BlockSilverfish$EnumType[] META_LOOKUP;
    private final int meta;
    private final String name;
    private final String unlocalizedName;

    static {
        META_LOOKUP = new BlockSilverfish$EnumType[BlockSilverfish$EnumType.values().length];
        BlockSilverfish$EnumType[] enumTypeArray = BlockSilverfish$EnumType.values();
        int n = enumTypeArray.length;
        int n2 = 0;
        while (n2 < n) {
            BlockSilverfish$EnumType blocksilverfish$enumtype;
            BlockSilverfish$EnumType.META_LOOKUP[blocksilverfish$enumtype.getMetadata()] = blocksilverfish$enumtype = enumTypeArray[n2];
            ++n2;
        }
    }

    private BlockSilverfish$EnumType(int meta, String name) {
        this(meta, name, name);
    }

    private BlockSilverfish$EnumType(int meta, String name, String unlocalizedName) {
        this.meta = meta;
        this.name = name;
        this.unlocalizedName = unlocalizedName;
    }

    public int getMetadata() {
        return this.meta;
    }

    public String toString() {
        return this.name;
    }

    public static BlockSilverfish$EnumType byMetadata(int meta) {
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

    public abstract IBlockState getModelBlock();

    public static BlockSilverfish$EnumType forModelBlock(IBlockState model) {
        BlockSilverfish$EnumType[] enumTypeArray = BlockSilverfish$EnumType.values();
        int n = enumTypeArray.length;
        int n2 = 0;
        while (n2 < n) {
            BlockSilverfish$EnumType blocksilverfish$enumtype = enumTypeArray[n2];
            if (model == blocksilverfish$enumtype.getModelBlock()) {
                return blocksilverfish$enumtype;
            }
            ++n2;
        }
        return STONE;
    }
}
