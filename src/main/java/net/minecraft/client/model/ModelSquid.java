package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSquid
extends ModelBase {
    ModelRenderer squidBody;
    ModelRenderer[] squidTentacles = new ModelRenderer[8];

    public ModelSquid() {
        int i = -16;
        this.squidBody = new ModelRenderer(this, 0, 0);
        this.squidBody.addBox(-6.0f, -8.0f, -6.0f, 12, 16, 12);
        this.squidBody.rotationPointY += (float)(24 + i);
        int j = 0;
        while (j < this.squidTentacles.length) {
            this.squidTentacles[j] = new ModelRenderer(this, 48, 0);
            double d0 = (double)j * Math.PI * 2.0 / (double)this.squidTentacles.length;
            float f = (float)Math.cos(d0) * 5.0f;
            float f1 = (float)Math.sin(d0) * 5.0f;
            this.squidTentacles[j].addBox(-1.0f, 0.0f, -1.0f, 2, 18, 2);
            this.squidTentacles[j].rotationPointX = f;
            this.squidTentacles[j].rotationPointZ = f1;
            this.squidTentacles[j].rotationPointY = 31 + i;
            d0 = (double)j * Math.PI * -2.0 / (double)this.squidTentacles.length + 1.5707963267948966;
            this.squidTentacles[j].rotateAngleY = (float)d0;
            ++j;
        }
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        ModelRenderer[] modelRendererArray = this.squidTentacles;
        int n = this.squidTentacles.length;
        int n2 = 0;
        while (n2 < n) {
            ModelRenderer modelrenderer = modelRendererArray[n2];
            modelrenderer.rotateAngleX = ageInTicks;
            ++n2;
        }
    }

    @Override
    public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
        this.squidBody.render(scale);
        int i = 0;
        while (i < this.squidTentacles.length) {
            this.squidTentacles[i].render(scale);
            ++i;
        }
    }
}
