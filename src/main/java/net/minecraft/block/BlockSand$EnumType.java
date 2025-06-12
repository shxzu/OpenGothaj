package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.util.IStringSerializable;

public enum BlockSand$EnumType implements IStringSerializable
{
    SAND(0, "sand", "default", MapColor.sandColor),
    RED_SAND(1, "red_sand", "red", MapColor.adobeColor);

    private static final BlockSand$EnumType[] META_LOOKUP;
    private final int meta;
    private final String name;
    private final MapColor mapColor;
    private final String unlocalizedName;

    static {
        META_LOOKUP = new BlockSand$EnumType[BlockSand$EnumType.values().length];
        BlockSand$EnumType[] enumTypeArray = BlockSand$EnumType.values();
        int n = enumTypeArray.length;
        int n2 = 0;
        while (n2 < n) {
            BlockSand$EnumType blocksand$enumtype;
            BlockSand$EnumType.META_LOOKUP[blocksand$enumtype.getMetadata()] = blocksand$enumtype = enumTypeArray[n2];
            ++n2;
        }
    }

    private BlockSand$EnumType(int meta, String name, String unlocalizedName, MapColor mapColor) {
        this.meta = meta;
        this.name = name;
        this.mapColor = mapColor;
        this.unlocalizedName = unlocalizedName;
    }

    public int getMetadata() {
        return this.meta;
    }

    public String toString() {
        return this.name;
    }

    public MapColor getMapColor() {
        return this.mapColor;
    }

    public static BlockSand$EnumType byMetadata(int meta) {
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
