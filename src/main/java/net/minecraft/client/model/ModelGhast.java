package net.minecraft.client.model;

import java.util.Random;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelGhast
extends ModelBase {
    ModelRenderer body;
    ModelRenderer[] tentacles = new ModelRenderer[9];

    public ModelGhast() {
        int i = -16;
        this.body = new ModelRenderer(this, 0, 0);
        this.body.addBox(-8.0f, -8.0f, -8.0f, 16, 16, 16);
        this.body.rotationPointY += (float)(24 + i);
        Random random = new Random(1660L);
        int j = 0;
        while (j < this.tentacles.length) {
            this.tentacles[j] = new ModelRenderer(this, 0, 0);
            float f = (((float)(j % 3) - (float)(j / 3 % 2) * 0.5f + 0.25f) / 2.0f * 2.0f - 1.0f) * 5.0f;
            float f1 = ((float)(j / 3) / 2.0f * 2.0f - 1.0f) * 5.0f;
            int k = random.nextInt(7) + 8;
            this.tentacles[j].addBox(-1.0f, 0.0f, -1.0f, 2, k, 2);
            this.tentacles[j].rotationPointX = f;
            this.tentacles[j].rotationPointZ = f1;
            this.tentacles[j].rotationPointY = 31 + i;
            ++j;
        }
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        int i = 0;
        while (i < this.tentacles.length) {
            this.tentacles[i].rotateAngleX = 0.2f * MathHelper.sin(ageInTicks * 0.3f + (float)i) + 0.4f;
            ++i;
        }
    }

    @Override
    public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0f, 0.6f, 0.0f);
        this.body.render(scale);
        ModelRenderer[] modelRendererArray = this.tentacles;
        int n = this.tentacles.length;
        int n2 = 0;
        while (n2 < n) {
            ModelRenderer modelrenderer = modelRendererArray[n2];
            modelrenderer.render(scale);
            ++n2;
        }
        GlStateManager.popMatrix();
    }
}
