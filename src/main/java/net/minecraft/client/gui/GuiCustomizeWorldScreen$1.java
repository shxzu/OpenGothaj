package net.minecraft.client.gui;

import com.google.common.base.Predicate;
import com.google.common.primitives.Floats;

class GuiCustomizeWorldScreen$1
implements Predicate<String> {
    GuiCustomizeWorldScreen$1() {
    }

    @Override
    public boolean apply(String p_apply_1_) {
        Float f = Floats.tryParse(p_apply_1_);
        return p_apply_1_.length() == 0 || f != null && Floats.isFinite(f.floatValue()) && f.floatValue() >= 0.0f;
    }
}
