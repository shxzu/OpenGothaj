package net.minecraft.client.renderer.block.model;

import java.util.List;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition;

public class ModelBlockDefinition$Variants {
    private final String name;
    private final List<ModelBlockDefinition.Variant> listVariants;

    public ModelBlockDefinition$Variants(String nameIn, List<ModelBlockDefinition.Variant> listVariantsIn) {
        this.name = nameIn;
        this.listVariants = listVariantsIn;
    }

    public List<ModelBlockDefinition.Variant> getVariants() {
        return this.listVariants;
    }

    public boolean equals(Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof ModelBlockDefinition$Variants)) {
            return false;
        }
        ModelBlockDefinition$Variants modelblockdefinition$variants = (ModelBlockDefinition$Variants)p_equals_1_;
        return !this.name.equals(modelblockdefinition$variants.name) ? false : this.listVariants.equals(modelblockdefinition$variants.listVariants);
    }

    public int hashCode() {
        int i = this.name.hashCode();
        i = 31 * i + this.listVariants.hashCode();
        return i;
    }

    static String access$0(ModelBlockDefinition$Variants variants) {
        return variants.name;
    }
}
