package net.minecraft.client.renderer.block.model;

import net.minecraft.util.EnumFacing;

enum ItemModelGenerator$SpanFacing {
    UP(EnumFacing.UP, 0, -1),
    DOWN(EnumFacing.DOWN, 0, 1),
    LEFT(EnumFacing.EAST, -1, 0),
    RIGHT(EnumFacing.WEST, 1, 0);

    private final EnumFacing facing;
    private final int field_178373_f;
    private final int field_178374_g;

    private ItemModelGenerator$SpanFacing(EnumFacing facing, int p_i46215_4_, int p_i46215_5_) {
        this.facing = facing;
        this.field_178373_f = p_i46215_4_;
        this.field_178374_g = p_i46215_5_;
    }

    public EnumFacing getFacing() {
        return this.facing;
    }

    public int func_178372_b() {
        return this.field_178373_f;
    }

    public int func_178371_c() {
        return this.field_178374_g;
    }

    private boolean func_178369_d() {
        return this == DOWN || this == UP;
    }

    static boolean access$2(ItemModelGenerator$SpanFacing spanFacing) {
        return spanFacing.func_178369_d();
    }
}
