package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelEnderMite
extends ModelBase {
    private static final int[][] field_178716_a = new int[][]{{4, 3, 2}, {6, 4, 5}, {3, 3, 1}, {1, 2, 1}};
    private static final int[][] field_178714_b;
    private static final int field_178715_c;
    private final ModelRenderer[] field_178713_d = new ModelRenderer[field_178715_c];

    static {
        int[][] nArrayArray = new int[4][];
        nArrayArray[0] = new int[2];
        int[] nArray = new int[2];
        nArray[1] = 5;
        nArrayArray[1] = nArray;
        int[] nArray2 = new int[2];
        nArray2[1] = 14;
        nArrayArray[2] = nArray2;
        int[] nArray3 = new int[2];
        nArray3[1] = 18;
        nArrayArray[3] = nArray3;
        field_178714_b = nArrayArray;
        field_178715_c = field_178716_a.length;
    }

    public ModelEnderMite() {
        float f = -3.5f;
        int i = 0;
        while (i < this.field_178713_d.length) {
            this.field_178713_d[i] = new ModelRenderer(this, field_178714_b[i][0], field_178714_b[i][1]);
            this.field_178713_d[i].addBox((float)field_178716_a[i][0] * -0.5f, 0.0f, (float)field_178716_a[i][2] * -0.5f, field_178716_a[i][0], field_178716_a[i][1], field_178716_a[i][2]);
            this.field_178713_d[i].setRotationPoint(0.0f, 24 - field_178716_a[i][1], f);
            if (i < this.field_178713_d.length - 1) {
                f += (float)(field_178716_a[i][2] + field_178716_a[i + 1][2]) * 0.5f;
            }
            ++i;
        }
    }

    @Override
    public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
        int i = 0;
        while (i < this.field_178713_d.length) {
            this.field_178713_d[i].render(scale);
            ++i;
        }
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        int i = 0;
        while (i < this.field_178713_d.length) {
            this.field_178713_d[i].rotateAngleY = MathHelper.cos(ageInTicks * 0.9f + (float)i * 0.15f * (float)Math.PI) * (float)Math.PI * 0.01f * (float)(1 + Math.abs(i - 2));
            this.field_178713_d[i].rotationPointX = MathHelper.sin(ageInTicks * 0.9f + (float)i * 0.15f * (float)Math.PI) * (float)Math.PI * 0.1f * (float)Math.abs(i - 2);
            ++i;
        }
    }
}
