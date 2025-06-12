package net.minecraft.block.state;

import com.google.common.base.Function;
import net.minecraft.block.properties.IProperty;

class BlockState$1
implements Function<IProperty, String> {
    BlockState$1() {
    }

    @Override
    public String apply(IProperty p_apply_1_) {
        return p_apply_1_ == null ? "<NULL>" : p_apply_1_.getName();
    }
}
