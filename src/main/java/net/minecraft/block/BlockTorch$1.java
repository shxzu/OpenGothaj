package net.minecraft.block;

import com.google.common.base.Predicate;
import net.minecraft.util.EnumFacing;

class BlockTorch$1
implements Predicate<EnumFacing> {
    BlockTorch$1() {
    }

    @Override
    public boolean apply(EnumFacing p_apply_1_) {
        return p_apply_1_ != EnumFacing.DOWN;
    }
}
