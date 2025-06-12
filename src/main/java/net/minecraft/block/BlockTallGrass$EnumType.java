package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockTallGrass$EnumType implements IStringSerializable
{
    DEAD_BUSH(0, "dead_bush"),
    GRASS(1, "tall_grass"),
    FERN(2, "fern");

    private static final BlockTallGrass$EnumType[] META_LOOKUP;
    private final int meta;
    private final String name;

    static {
        META_LOOKUP = new BlockTallGrass$EnumType[BlockTallGrass$EnumType.values().length];
        BlockTallGrass$EnumType[] enumTypeArray = BlockTallGrass$EnumType.values();
        int n = enumTypeArray.length;
        int n2 = 0;
        while (n2 < n) {
            BlockTallGrass$EnumType blocktallgrass$enumtype;
            BlockTallGrass$EnumType.META_LOOKUP[blocktallgrass$enumtype.getMeta()] = blocktallgrass$enumtype = enumTypeArray[n2];
            ++n2;
        }
    }

    private BlockTallGrass$EnumType(int meta, String name) {
        this.meta = meta;
        this.name = name;
    }

    public int getMeta() {
        return this.meta;
    }

    public String toString() {
        return this.name;
    }

    public static BlockTallGrass$EnumType byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }
        return META_LOOKUP[meta];
    }

    @Override
    public String getName() {
        return this.name;
    }
}
