package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockQuartz$EnumType implements IStringSerializable
{
    DEFAULT(0, "default", "default"),
    CHISELED(1, "chiseled", "chiseled"),
    LINES_Y(2, "lines_y", "lines"),
    LINES_X(3, "lines_x", "lines"),
    LINES_Z(4, "lines_z", "lines");

    private static final BlockQuartz$EnumType[] META_LOOKUP;
    private final int meta;
    private final String field_176805_h;
    private final String unlocalizedName;

    static {
        META_LOOKUP = new BlockQuartz$EnumType[BlockQuartz$EnumType.values().length];
        BlockQuartz$EnumType[] enumTypeArray = BlockQuartz$EnumType.values();
        int n = enumTypeArray.length;
        int n2 = 0;
        while (n2 < n) {
            BlockQuartz$EnumType blockquartz$enumtype;
            BlockQuartz$EnumType.META_LOOKUP[blockquartz$enumtype.getMetadata()] = blockquartz$enumtype = enumTypeArray[n2];
            ++n2;
        }
    }

    private BlockQuartz$EnumType(int meta, String name, String unlocalizedName) {
        this.meta = meta;
        this.field_176805_h = name;
        this.unlocalizedName = unlocalizedName;
    }

    public int getMetadata() {
        return this.meta;
    }

    public String toString() {
        return this.unlocalizedName;
    }

    public static BlockQuartz$EnumType byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }
        return META_LOOKUP[meta];
    }

    @Override
    public String getName() {
        return this.field_176805_h;
    }
}
