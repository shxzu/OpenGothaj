package net.minecraft.client.renderer;

import net.minecraft.util.EnumFacing;

enum BlockModelRenderer$VertexTranslations {
    DOWN(0, 1, 2, 3),
    UP(2, 3, 0, 1),
    NORTH(3, 0, 1, 2),
    SOUTH(0, 1, 2, 3),
    WEST(3, 0, 1, 2),
    EAST(1, 2, 3, 0);

    private final int field_178191_g;
    private final int field_178200_h;
    private final int field_178201_i;
    private final int field_178198_j;
    private static final BlockModelRenderer$VertexTranslations[] VALUES;

    static {
        VALUES = new BlockModelRenderer$VertexTranslations[6];
        BlockModelRenderer$VertexTranslations.VALUES[EnumFacing.DOWN.getIndex()] = DOWN;
        BlockModelRenderer$VertexTranslations.VALUES[EnumFacing.UP.getIndex()] = UP;
        BlockModelRenderer$VertexTranslations.VALUES[EnumFacing.NORTH.getIndex()] = NORTH;
        BlockModelRenderer$VertexTranslations.VALUES[EnumFacing.SOUTH.getIndex()] = SOUTH;
        BlockModelRenderer$VertexTranslations.VALUES[EnumFacing.WEST.getIndex()] = WEST;
        BlockModelRenderer$VertexTranslations.VALUES[EnumFacing.EAST.getIndex()] = EAST;
    }

    private BlockModelRenderer$VertexTranslations(int p_i46234_3_, int p_i46234_4_, int p_i46234_5_, int p_i46234_6_) {
        this.field_178191_g = p_i46234_3_;
        this.field_178200_h = p_i46234_4_;
        this.field_178201_i = p_i46234_5_;
        this.field_178198_j = p_i46234_6_;
    }

    public static BlockModelRenderer$VertexTranslations getVertexTranslations(EnumFacing p_178184_0_) {
        return VALUES[p_178184_0_.getIndex()];
    }

    static int access$2(BlockModelRenderer$VertexTranslations vertexTranslations) {
        return vertexTranslations.field_178191_g;
    }

    static int access$3(BlockModelRenderer$VertexTranslations vertexTranslations) {
        return vertexTranslations.field_178200_h;
    }

    static int access$4(BlockModelRenderer$VertexTranslations vertexTranslations) {
        return vertexTranslations.field_178201_i;
    }

    static int access$5(BlockModelRenderer$VertexTranslations vertexTranslations) {
        return vertexTranslations.field_178198_j;
    }
}
