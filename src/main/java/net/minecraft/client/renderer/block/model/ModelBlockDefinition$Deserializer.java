package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
import net.minecraft.util.JsonUtils;

public class ModelBlockDefinition$Deserializer
implements JsonDeserializer<ModelBlockDefinition> {
    public ModelBlockDefinition deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
        JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
        List<ModelBlockDefinition.Variants> list = this.parseVariantsList(p_deserialize_3_, jsonobject);
        return new ModelBlockDefinition((Collection<ModelBlockDefinition.Variants>)list);
    }

    protected List<ModelBlockDefinition.Variants> parseVariantsList(JsonDeserializationContext p_178334_1_, JsonObject p_178334_2_) {
        JsonObject jsonobject = JsonUtils.getJsonObject(p_178334_2_, "variants");
        ArrayList<ModelBlockDefinition.Variants> list = Lists.newArrayList();
        for (Map.Entry entry : jsonobject.entrySet()) {
            list.add(this.parseVariants(p_178334_1_, entry));
        }
        return list;
    }

    protected ModelBlockDefinition.Variants parseVariants(JsonDeserializationContext p_178335_1_, Map.Entry<String, JsonElement> p_178335_2_) {
        String s = p_178335_2_.getKey();
        ArrayList<ModelBlockDefinition.Variant> list = Lists.newArrayList();
        JsonElement jsonelement = p_178335_2_.getValue();
        if (jsonelement.isJsonArray()) {
            for (JsonElement jsonelement1 : jsonelement.getAsJsonArray()) {
                list.add((ModelBlockDefinition.Variant)p_178335_1_.deserialize(jsonelement1, ModelBlockDefinition.Variant.class));
            }
        } else {
            list.add((ModelBlockDefinition.Variant)p_178335_1_.deserialize(jsonelement, ModelBlockDefinition.Variant.class));
        }
        return new ModelBlockDefinition.Variants(s, list);
    }
}
