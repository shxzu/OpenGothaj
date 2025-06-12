package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import com.viaversion.viarewind.utils.ChatUtil;
import com.viaversion.viaversion.api.minecraft.item.DataItem;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.util.ChatColorUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameProfileStorage$GameProfile {
    public final String name;
    public final UUID uuid;
    public String displayName;
    public int ping;
    public List<GameProfileStorage.Property> properties = new ArrayList<GameProfileStorage.Property>();
    public int gamemode = 0;

    public GameProfileStorage$GameProfile(UUID uuid, String name) {
        this.name = name;
        this.uuid = uuid;
    }

    public Item getSkull() {
        CompoundTag tag = new CompoundTag();
        CompoundTag ownerTag = new CompoundTag();
        tag.put("SkullOwner", ownerTag);
        ownerTag.put("Id", new StringTag(this.uuid.toString()));
        CompoundTag properties = new CompoundTag();
        ownerTag.put("Properties", properties);
        ListTag textures = new ListTag(CompoundTag.class);
        properties.put("textures", textures);
        for (GameProfileStorage.Property property : this.properties) {
            if (!property.name.equals("textures")) continue;
            CompoundTag textureTag = new CompoundTag();
            textureTag.put("Value", new StringTag(property.value));
            if (property.signature != null) {
                textureTag.put("Signature", new StringTag(property.signature));
            }
            textures.add(textureTag);
        }
        return new DataItem(397, 1, 3, tag);
    }

    public String getDisplayName() {
        String displayName;
        String string = displayName = this.displayName == null ? this.name : this.displayName;
        if (displayName.length() > 16) {
            displayName = ChatUtil.removeUnusedColor(displayName, 'f');
        }
        if (displayName.length() > 16) {
            displayName = ChatColorUtil.stripColor(displayName);
        }
        if (displayName.length() > 16) {
            displayName = displayName.substring(0, 16);
        }
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
