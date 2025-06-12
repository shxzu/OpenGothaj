package net.minecraft.block.state;

import com.google.common.base.Function;
import java.util.Map;
import net.minecraft.block.properties.IProperty;

class BlockStateBase$1
implements Function<Map.Entry<IProperty, Comparable>, String> {
    BlockStateBase$1() {
    }

    @Override
    public String apply(Map.Entry<IProperty, Comparable> p_apply_1_) {
        if (p_apply_1_ == null) {
            return "<NULL>";
        }
        IProperty iproperty = p_apply_1_.getKey();
        return String.valueOf(iproperty.getName()) + "=" + iproperty.getName(p_apply_1_.getValue());
    }
}
