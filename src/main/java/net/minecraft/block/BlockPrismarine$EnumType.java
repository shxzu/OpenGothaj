package net.minecraft.block;

import net.minecraft.util.IStringSerializable;

public enum BlockPrismarine$EnumType implements IStringSerializable
{
    ROUGH(0, "prismarine", "rough"),
    BRICKS(1, "prismarine_bricks", "bricks"),
    DARK(2, "dark_prismarine", "dark");

    private static final BlockPrismarine$EnumType[] META_LOOKUP;
    private final int meta;
    private final String name;
    private final String unlocalizedName;

    static {
        META_LOOKUP = new BlockPrismarine$EnumType[BlockPrismarine$EnumType.values().length];
        BlockPrismarine$EnumType[] enumTypeArray = BlockPrismarine$EnumType.values();
        int n = enumTypeArray.length;
        int n2 = 0;
        while (n2 < n) {
            BlockPrismarine$EnumType blockprismarine$enumtype;
            BlockPrismarine$EnumType.META_LOOKUP[blockprismarine$enumtype.getMetadata()] = blockprismarine$enumtype = enumTypeArray[n2];
            ++n2;
        }
    }

    private BlockPrismarine$EnumType(int meta, String name, String unlocalizedName) {
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

    public static BlockPrismarine$EnumType byMetadata(int meta) {
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
