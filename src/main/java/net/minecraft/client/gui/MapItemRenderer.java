package net.minecraft.client.gui;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec4b;
import net.minecraft.world.storage.MapData;

public class MapItemRenderer {
    private static final ResourceLocation mapIcons = new ResourceLocation("textures/map/map_icons.png");
    private final TextureManager textureManager;
    private final Map<String, Instance> loadedMaps = Maps.newHashMap();

    public MapItemRenderer(TextureManager textureManagerIn) {
        this.textureManager = textureManagerIn;
    }

    public void updateMapTexture(MapData mapdataIn) {
        this.getMapRendererInstance(mapdataIn).updateMapTexture();
    }

    public void renderMap(MapData mapdataIn, boolean p_148250_2_) {
        this.getMapRendererInstance(mapdataIn).render(p_148250_2_);
    }

    private Instance getMapRendererInstance(MapData mapdataIn) {
        Instance mapitemrenderer$instance = this.loadedMaps.get(mapdataIn.mapName);
        if (mapitemrenderer$instance == null) {
            mapitemrenderer$instance = new Instance(mapdataIn);
            this.loadedMaps.put(mapdataIn.mapName, mapitemrenderer$instance);
        }
        return mapitemrenderer$instance;
    }

    public void clearLoadedMaps() {
        for (Instance mapitemrenderer$instance : this.loadedMaps.values()) {
            this.textureManager.deleteTexture(mapitemrenderer$instance.location);
        }
        this.loadedMaps.clear();
    }

    class Instance {
        private final MapData mapData;
        private final DynamicTexture mapTexture;
        private final ResourceLocation location;
        private final int[] mapTextureData;

        private Instance(MapData mapdataIn) {
            this.mapData = mapdataIn;
            this.mapTexture = new DynamicTexture(128, 128);
            this.mapTextureData = this.mapTexture.getTextureData();
            this.location = MapItemRenderer.this.textureManager.getDynamicTextureLocation("map/" + mapdataIn.mapName, this.mapTexture);
            int i = 0;
            while (i < this.mapTextureData.length) {
                this.mapTextureData[i] = 0;
                ++i;
            }
        }

        private void updateMapTexture() {
            int i = 0;
            while (i < 16384) {
                int j = this.mapData.colors[i] & 0xFF;
                this.mapTextureData[i] = j / 4 == 0 ? (i + i / 128 & 1) * 8 + 16 << 24 : MapColor.mapColorArray[j / 4].getMapColor(j & 3);
                ++i;
            }
            this.mapTexture.updateDynamicTexture();
        }

        private void render(boolean noOverlayRendering) {
            int i = 0;
            int j = 0;
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            float f = 0.0f;
            MapItemRenderer.this.textureManager.bindTexture(this.location);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(1, 771, 0, 1);
            GlStateManager.disableAlpha();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            worldrenderer.pos((float)(i + 0) + f, (float)(j + 128) - f, -0.01f).tex(0.0, 1.0).endVertex();
            worldrenderer.pos((float)(i + 128) - f, (float)(j + 128) - f, -0.01f).tex(1.0, 1.0).endVertex();
            worldrenderer.pos((float)(i + 128) - f, (float)(j + 0) + f, -0.01f).tex(1.0, 0.0).endVertex();
            worldrenderer.pos((float)(i + 0) + f, (float)(j + 0) + f, -0.01f).tex(0.0, 0.0).endVertex();
            tessellator.draw();
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            MapItemRenderer.this.textureManager.bindTexture(mapIcons);
            int k = 0;
            for (Vec4b vec4b : this.mapData.mapDecorations.values()) {
                if (noOverlayRendering && vec4b.func_176110_a() != 1) continue;
                GlStateManager.pushMatrix();
                GlStateManager.translate((float)i + (float)vec4b.func_176112_b() / 2.0f + 64.0f, (float)j + (float)vec4b.func_176113_c() / 2.0f + 64.0f, -0.02f);
                GlStateManager.rotate((float)(vec4b.func_176111_d() * 360) / 16.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.scale(4.0f, 4.0f, 3.0f);
                GlStateManager.translate(-0.125f, 0.125f, 0.0f);
                byte b0 = vec4b.func_176110_a();
                float f1 = (float)(b0 % 4 + 0) / 4.0f;
                float f2 = (float)(b0 / 4 + 0) / 4.0f;
                float f3 = (float)(b0 % 4 + 1) / 4.0f;
                float f4 = (float)(b0 / 4 + 1) / 4.0f;
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
                float f5 = -0.001f;
                worldrenderer.pos(-1.0, 1.0, (float)k * -0.001f).tex(f1, f2).endVertex();
                worldrenderer.pos(1.0, 1.0, (float)k * -0.001f).tex(f3, f2).endVertex();
                worldrenderer.pos(1.0, -1.0, (float)k * -0.001f).tex(f3, f4).endVertex();
                worldrenderer.pos(-1.0, -1.0, (float)k * -0.001f).tex(f1, f4).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
                ++k;
            }
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0f, 0.0f, -0.04f);
            GlStateManager.scale(1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
    }
}
