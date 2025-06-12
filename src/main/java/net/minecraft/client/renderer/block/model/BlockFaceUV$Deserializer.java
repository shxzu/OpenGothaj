package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.util.JsonUtils;

class BlockFaceUV$Deserializer
implements JsonDeserializer<BlockFaceUV> {
    BlockFaceUV$Deserializer() {
    }

    public BlockFaceUV deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
        JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
        float[] afloat = this.parseUV(jsonobject);
        int i = this.parseRotation(jsonobject);
        return new BlockFaceUV(afloat, i);
    }

    protected int parseRotation(JsonObject p_178291_1_) {
        int i = JsonUtils.getInt(p_178291_1_, "rotation", 0);
        if (i >= 0 && i % 90 == 0 && i / 90 <= 3) {
            return i;
        }
        throw new JsonParseException("Invalid rotation " + i + " found, only 0/90/180/270 allowed");
    }

    private float[] parseUV(JsonObject p_178292_1_) {
        if (!p_178292_1_.has("uv")) {
            return null;
        }
        JsonArray jsonarray = JsonUtils.getJsonArray(p_178292_1_, "uv");
        if (jsonarray.size() != 4) {
            throw new JsonParseException("Expected 4 uv values, found: " + jsonarray.size());
        }
        float[] afloat = new float[4];
        int i = 0;
        while (i < afloat.length) {
            afloat[i] = JsonUtils.getFloat(jsonarray.get(i), "uv[" + i + "]");
            ++i;
        }
        return afloat;
    }
}
