package xyz.cucumber.base.utils.render;

import java.awt.Color;
import xyz.cucumber.base.module.settings.ColorSettings;

public class ColorUtils {
    public static int mix(int c1, int c2, double size, double max) {
        int f3 = c1 >> 24 & 0xFF;
        int f4 = c1 >> 24 & 0xFF;
        Color col1 = new Color(c1);
        Color col2 = new Color(c2);
        int diffR = (int)((double)col1.getRed() - (double)(col1.getRed() - col2.getRed()) / max * size);
        int diffG = (int)((double)col1.getGreen() - (double)(col1.getGreen() - col2.getGreen()) / max * size);
        int diffB = (int)((double)col1.getBlue() - (double)(col1.getBlue() - col2.getBlue()) / max * size);
        if (diffR > 255) {
            diffR = 255;
        }
        if (diffR < 0) {
            diffR = 0;
        }
        if (diffG > 255) {
            diffG = 255;
        }
        if (diffG < 0) {
            diffG = 0;
        }
        if (diffB > 255) {
            diffB = 255;
        }
        if (diffB < 0) {
            diffB = 0;
        }
        return new Color(diffR, diffG, diffB).getRGB();
    }

    public static int skyRainbow(double offset, float time, double speed) {
        return Color.HSBtoRGB((float)((int)((double)time / speed + offset)) % 360.0f / 360.0f, 0.5f, 1.0f);
    }

    public static int rainbow(double speed, double offset) {
        return Color.HSBtoRGB((float)((double)(System.nanoTime() / 1000000L) / speed + offset) % 360.0f / 360.0f, 1.0f, 1.0f);
    }

    public static int rainbow(double speed, double offset, double milis) {
        return Color.HSBtoRGB((float)(milis / speed + offset) % 360.0f / 360.0f, 1.0f, 1.0f);
    }

    public static int getAlphaColor(int color, int alpha) {
        int newAlpha = 255 * alpha / 100;
        return new Color(new Color(color).getRed(), new Color(color).getGreen(), new Color(color).getBlue(), newAlpha).getRGB();
    }

    public static int getColor(ColorSettings color, double milis, double offset, double speed) {
        int c = ColorUtils.getAlphaColor(color.getMainColor(), color.getAlpha());
        switch (color.getMode().toLowerCase()) {
            case "rainbow": {
                c = ColorUtils.getAlphaColor(ColorUtils.rainbow(speed, offset, milis), color.getAlpha());
                break;
            }
            case "mix": {
                c = ColorUtils.getAlphaColor(ColorUtils.mix(color.getMainColor(), color.getSecondaryColor(), 1.0 + Math.cos(Math.toRadians(milis / speed + offset)), 2.0), color.getAlpha());
                break;
            }
            case "sky": {
                c = ColorUtils.getAlphaColor(ColorUtils.skyRainbow(offset, (float)milis, speed), color.getAlpha());
            }
        }
        return c;
    }
}
