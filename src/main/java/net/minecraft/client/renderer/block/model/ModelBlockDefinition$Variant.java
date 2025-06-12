package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class ModelBlockDefinition$Variant {
    private final ResourceLocation modelLocation;
    private final ModelRotation modelRotation;
    private final boolean uvLock;
    private final int weight;

    public ModelBlockDefinition$Variant(ResourceLocation modelLocationIn, ModelRotation modelRotationIn, boolean uvLockIn, int weightIn) {
        this.modelLocation = modelLocationIn;
        this.modelRotation = modelRotationIn;
        this.uvLock = uvLockIn;
        this.weight = weightIn;
    }

    public ResourceLocation getModelLocation() {
        return this.modelLocation;
    }

    public ModelRotation getRotation() {
        return this.modelRotation;
    }

    public boolean isUvLocked() {
        return this.uvLock;
    }

    public int getWeight() {
        return this.weight;
    }

    public boolean equals(Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof ModelBlockDefinition$Variant)) {
            return false;
        }
        ModelBlockDefinition$Variant modelblockdefinition$variant = (ModelBlockDefinition$Variant)p_equals_1_;
        return this.modelLocation.equals(modelblockdefinition$variant.modelLocation) && this.modelRotation == modelblockdefinition$variant.modelRotation && this.uvLock == modelblockdefinition$variant.uvLock;
    }

    public int hashCode() {
        int i = this.modelLocation.hashCode();
        i = 31 * i + (this.modelRotation != null ? this.modelRotation.hashCode() : 0);
        i = 31 * i + (this.uvLock ? 1 : 0);
        return i;
    }

    public static class Deserializer
    implements JsonDeserializer<ModelBlockDefinition$Variant> {
        public ModelBlockDefinition$Variant deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
            JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
            String s = this.parseModel(jsonobject);
            ModelRotation modelrotation = this.parseRotation(jsonobject);
            boolean flag = this.parseUvLock(jsonobject);
            int i = this.parseWeight(jsonobject);
            return new ModelBlockDefinition$Variant(this.makeModelLocation(s), modelrotation, flag, i);
        }

        private ResourceLocation makeModelLocation(String p_178426_1_) {
            ResourceLocation resourcelocation = new ResourceLocation(p_178426_1_);
            resourcelocation = new ResourceLocation(resourcelocation.getResourceDomain(), "block/" + resourcelocation.getResourcePath());
            return resourcelocation;
        }

        private boolean parseUvLock(JsonObject p_178429_1_) {
            return JsonUtils.getBoolean(p_178429_1_, "uvlock", false);
        }

        protected ModelRotation parseRotation(JsonObject p_178428_1_) {
            int j;
            int i = JsonUtils.getInt(p_178428_1_, "x", 0);
            ModelRotation modelrotation = ModelRotation.getModelRotation(i, j = JsonUtils.getInt(p_178428_1_, "y", 0));
            if (modelrotation == null) {
                throw new JsonParseException("Invalid BlockModelRotation x: " + i + ", y: " + j);
            }
            return modelrotation;
        }

        protected String parseModel(JsonObject p_178424_1_) {
            return JsonUtils.getString(p_178424_1_, "model");
        }

        protected int parseWeight(JsonObject p_178427_1_) {
            return JsonUtils.getInt(p_178427_1_, "weight", 1);
        }
    }
}
