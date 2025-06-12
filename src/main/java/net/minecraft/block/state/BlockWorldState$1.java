package net.minecraft.block.state;

import com.google.common.base.Predicate;
import net.minecraft.block.state.BlockWorldState;

class BlockWorldState$1
implements Predicate<BlockWorldState> {
    private final Predicate val$predicatesIn;

    BlockWorldState$1(Predicate predicate) {
        this.val$predicatesIn = predicate;
    }

    @Override
    public boolean apply(BlockWorldState p_apply_1_) {
        return p_apply_1_ != null && this.val$predicatesIn.apply(p_apply_1_.getBlockState());
    }
}
