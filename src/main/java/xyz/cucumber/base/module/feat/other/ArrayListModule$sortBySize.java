package xyz.cucumber.base.module.feat.other;

import java.util.Comparator;
import xyz.cucumber.base.module.feat.other.ArrayListModule;
import xyz.cucumber.base.utils.render.Fonts;

public class ArrayListModule$sortBySize
implements Comparator<ArrayListModule.ArrayModule> {
    @Override
    public int compare(ArrayListModule.ArrayModule o1, ArrayListModule.ArrayModule o2) {
        String font = ArrayListModule.this.fonts.getMode().toLowerCase();
        String n1 = String.valueOf(o1.module.getName(ArrayListModule.this.splitNames.isEnabled())) + ArrayListModule.this.getSuffix(o1);
        String n2 = String.valueOf(o2.module.getName(ArrayListModule.this.splitNames.isEnabled())) + ArrayListModule.this.getSuffix(o2);
        if (ArrayListModule.this.textStyle.getMode().toLowerCase().equals("lowercase")) {
            n1 = n1.toLowerCase();
            n2 = n2.toLowerCase();
        } else if (ArrayListModule.this.textStyle.getMode().toLowerCase().equals("uppercase")) {
            n1 = n1.toUpperCase();
            n2 = n2.toUpperCase();
        }
        if (Fonts.getFont(font).getWidth(n1) > Fonts.getFont(font).getWidth(n2)) {
            return -1;
        }
        return 1;
    }
}
