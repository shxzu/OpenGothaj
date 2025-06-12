package net.minecraft.block;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

public enum BlockLever$EnumOrientation implements IStringSerializable
{
    DOWN_X(0, "down_x", EnumFacing.DOWN),
    EAST(1, "east", EnumFacing.EAST),
    WEST(2, "west", EnumFacing.WEST),
    SOUTH(3, "south", EnumFacing.SOUTH),
    NORTH(4, "north", EnumFacing.NORTH),
    UP_Z(5, "up_z", EnumFacing.UP),
    UP_X(6, "up_x", EnumFacing.UP),
    DOWN_Z(7, "down_z", EnumFacing.DOWN);

    private static final BlockLever$EnumOrientation[] META_LOOKUP;
    private final int meta;
    private final String name;
    private final EnumFacing facing;

    static {
        META_LOOKUP = new BlockLever$EnumOrientation[BlockLever$EnumOrientation.values().length];
        BlockLever$EnumOrientation[] enumOrientationArray = BlockLever$EnumOrientation.values();
        int n = enumOrientationArray.length;
        int n2 = 0;
        while (n2 < n) {
            BlockLever$EnumOrientation blocklever$enumorientation;
            BlockLever$EnumOrientation.META_LOOKUP[blocklever$enumorientation.getMetadata()] = blocklever$enumorientation = enumOrientationArray[n2];
            ++n2;
        }
    }

    private BlockLever$EnumOrientation(int meta, String name, EnumFacing facing) {
        this.meta = meta;
        this.name = name;
        this.facing = facing;
    }

    public int getMetadata() {
        return this.meta;
    }

    public EnumFacing getFacing() {
        return this.facing;
    }

    public String toString() {
        return this.name;
    }

    public static BlockLever$EnumOrientation byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }
        return META_LOOKUP[meta];
    }

    public static BlockLever$EnumOrientation forFacings(EnumFacing clickedSide, EnumFacing entityFacing) {
        switch (clickedSide) {
            case DOWN: {
                switch (entityFacing.getAxis()) {
                    case X: {
                        return DOWN_X;
                    }
                    case Z: {
                        return DOWN_Z;
                    }
                }
                throw new IllegalArgumentException("Invalid entityFacing " + entityFacing + " for facing " + clickedSide);
            }
            case UP: {
                switch (entityFacing.getAxis()) {
                    case X: {
                        return UP_X;
                    }
                    case Z: {
                        return UP_Z;
                    }
                }
                throw new IllegalArgumentException("Invalid entityFacing " + entityFacing + " for facing " + clickedSide);
            }
            case NORTH: {
                return NORTH;
            }
            case SOUTH: {
                return SOUTH;
            }
            case WEST: {
                return WEST;
            }
            case EAST: {
                return EAST;
            }
        }
        throw new IllegalArgumentException("Invalid facing: " + clickedSide);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
