package com.viaversion.viabackwards.protocol.protocol1_20to1_20_2.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.packet.ClientboundPackets1_20_2;
import com.viaversion.viaversion.protocols.protocol1_20_2to1_20.rewriter.RecipeRewriter1_20_2;

class BlockItemPacketRewriter1_20_2$7
extends RecipeRewriter1_20_2<ClientboundPackets1_20_2> {
    BlockItemPacketRewriter1_20_2$7(Protocol arg0) {
        super(arg0);
    }

    @Override
    public void handleCraftingShapeless(PacketWrapper wrapper) throws Exception {
        wrapper.passthrough(Type.STRING);
        wrapper.passthrough(Type.VAR_INT);
        this.handleIngredients(wrapper);
        Item result = wrapper.read(this.itemType());
        this.rewrite(result);
        wrapper.write(Type.ITEM1_13_2, result);
    }

    @Override
    public void handleSmelting(PacketWrapper wrapper) throws Exception {
        wrapper.passthrough(Type.STRING);
        wrapper.passthrough(Type.VAR_INT);
        this.handleIngredient(wrapper);
        Item result = wrapper.read(this.itemType());
        this.rewrite(result);
        wrapper.write(Type.ITEM1_13_2, result);
        wrapper.passthrough(Type.FLOAT);
        wrapper.passthrough(Type.VAR_INT);
    }

    @Override
    public void handleCraftingShaped(PacketWrapper wrapper) throws Exception {
        int ingredients = wrapper.passthrough(Type.VAR_INT) * wrapper.passthrough(Type.VAR_INT);
        wrapper.passthrough(Type.STRING);
        wrapper.passthrough(Type.VAR_INT);
        for (int i = 0; i < ingredients; ++i) {
            this.handleIngredient(wrapper);
        }
        Item result = wrapper.read(this.itemType());
        this.rewrite(result);
        wrapper.write(Type.ITEM1_13_2, result);
        wrapper.passthrough(Type.BOOLEAN);
    }

    @Override
    public void handleStonecutting(PacketWrapper wrapper) throws Exception {
        wrapper.passthrough(Type.STRING);
        this.handleIngredient(wrapper);
        Item result = wrapper.read(this.itemType());
        this.rewrite(result);
        wrapper.write(Type.ITEM1_13_2, result);
    }

    @Override
    public void handleSmithing(PacketWrapper wrapper) throws Exception {
        this.handleIngredient(wrapper);
        this.handleIngredient(wrapper);
        Item result = wrapper.read(this.itemType());
        this.rewrite(result);
        wrapper.write(Type.ITEM1_13_2, result);
    }

    @Override
    public void handleSmithingTransform(PacketWrapper wrapper) throws Exception {
        this.handleIngredient(wrapper);
        this.handleIngredient(wrapper);
        this.handleIngredient(wrapper);
        Item result = wrapper.read(this.itemType());
        this.rewrite(result);
        wrapper.write(Type.ITEM1_13_2, result);
    }

    @Override
    protected void handleIngredient(PacketWrapper wrapper) throws Exception {
        Item[] items = wrapper.read(this.itemArrayType());
        wrapper.write(Type.ITEM1_13_2_ARRAY, items);
        for (Item item : items) {
            this.rewrite(item);
        }
    }
}
