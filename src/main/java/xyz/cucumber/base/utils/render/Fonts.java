package xyz.cucumber.base.utils.render;

import java.awt.Font;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import xyz.cucumber.base.utils.render.FontUtils;

public class Fonts {
    private static HashMap<String[], FontUtils> fonts = new HashMap();

    public Fonts() {
        fonts.put(new String[]{"rb-r"}, new FontUtils(Fonts.getResource(30.0f, false, new ResourceLocation(this.getLocation("Roboto-Regular")))));
        fonts.put(new String[]{"rb-m"}, new FontUtils(Fonts.getResource(30.0f, false, new ResourceLocation(this.getLocation("Roboto-Medium")))));
        fonts.put(new String[]{"rb-b"}, new FontUtils(Fonts.getResource(30.0f, false, new ResourceLocation(this.getLocation("Roboto-Bold")))));
        fonts.put(new String[]{"rb-b-h"}, new FontUtils(Fonts.getResource(44.0f, false, new ResourceLocation(this.getLocation("Roboto-Bold")))));
        fonts.put(new String[]{"rb-r-13"}, new FontUtils(Fonts.getResource(26.0f, false, new ResourceLocation(this.getLocation("Roboto-Regular")))));
        fonts.put(new String[]{"rb-m-13"}, new FontUtils(Fonts.getResource(26.0f, false, new ResourceLocation(this.getLocation("Roboto-Medium")))));
        fonts.put(new String[]{"rb-b-13"}, new FontUtils(Fonts.getResource(26.0f, false, new ResourceLocation(this.getLocation("Roboto-Bold")))));
        fonts.put(new String[]{"comforta"}, new FontUtils(Fonts.getResource(30.0f, false, new ResourceLocation(this.getLocation("comfortaa")))));
        fonts.put(new String[]{"8-bits"}, new FontUtils(Fonts.getResource(30.0f, false, new ResourceLocation(this.getLocation("EightBits")))));
        fonts.put(new String[]{"orbitron"}, new FontUtils(Fonts.getResource(30.0f, false, new ResourceLocation(this.getLocation("Orbitron")))));
        fonts.put(new String[]{"volte"}, new FontUtils(Fonts.getResource(30.0f, false, new ResourceLocation(this.getLocation("Volte-Semibold")))));
        fonts.put(new String[]{"minecraft"}, new FontUtils(Fonts.getResource(26.0f, false, new ResourceLocation(this.getLocation("minecraft")))));
        fonts.put(new String[]{"bebas"}, new FontUtils(Fonts.getResource(30.0f, false, new ResourceLocation(this.getLocation("bebas")))));
        fonts.put(new String[]{"mitr"}, new FontUtils(Fonts.getResource(30.0f, false, new ResourceLocation(this.getLocation("Mitr")))));
    }

    public static FontUtils getFont(String font) {
        for (Map.Entry<String[], FontUtils> entry : fonts.entrySet()) {
            for (String s : Arrays.asList(entry.getKey())) {
                boolean valid = s.toLowerCase().equals(font.toLowerCase());
                if (!valid) continue;
                return entry.getValue();
            }
        }
        return null;
    }

    private String getLocation(String name) {
        return "client/fonts/" + name + ".ttf";
    }

    private static Font getResource(float size, boolean bold, ResourceLocation fontFile) {
        Font font = null;
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(fontFile).getInputStream();
            font = Font.createFont(0, is);
            font = font.deriveFont(0, size);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error loading font, setted to verdana.");
            font = new Font("Verdana", 0, 12);
        }
        return font;
    }
}
