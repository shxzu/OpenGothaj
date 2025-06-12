package net.minecraft.client.network;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.util.ResourceLocation;

class NetworkPlayerInfo$1
implements SkinManager.SkinAvailableCallback {
    NetworkPlayerInfo$1() {
    }

    @Override
    public void skinAvailable(MinecraftProfileTexture.Type p_180521_1_, ResourceLocation location, MinecraftProfileTexture profileTexture) {
        switch (p_180521_1_) {
            case SKIN: {
                NetworkPlayerInfo.this.locationSkin = location;
                NetworkPlayerInfo.this.skinType = profileTexture.getMetadata("model");
                if (NetworkPlayerInfo.this.skinType != null) break;
                NetworkPlayerInfo.this.skinType = "default";
                break;
            }
            case CAPE: {
                NetworkPlayerInfo.this.locationCape = location;
            }
        }
    }
}
