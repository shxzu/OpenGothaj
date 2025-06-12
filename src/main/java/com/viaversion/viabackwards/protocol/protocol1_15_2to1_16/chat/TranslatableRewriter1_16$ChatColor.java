package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat;

final class TranslatableRewriter1_16$ChatColor {
    private final String colorName;
    private final int rgb;
    private final int r;
    private final int g;
    private final int b;

    TranslatableRewriter1_16$ChatColor(String colorName, int rgb) {
        this.colorName = colorName;
        this.rgb = rgb;
        this.r = rgb >> 16 & 0xFF;
        this.g = rgb >> 8 & 0xFF;
        this.b = rgb & 0xFF;
    }

    static int access$000(TranslatableRewriter1_16$ChatColor x0) {
        return x0.rgb;
    }

    static String access$100(TranslatableRewriter1_16$ChatColor x0) {
        return x0.colorName;
    }

    static int access$200(TranslatableRewriter1_16$ChatColor x0) {
        return x0.r;
    }

    static int access$300(TranslatableRewriter1_16$ChatColor x0) {
        return x0.g;
    }

    static int access$400(TranslatableRewriter1_16$ChatColor x0) {
        return x0.b;
    }
}
