package xyz.cucumber.base.module.feat.other;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventBloom;
import xyz.cucumber.base.events.ext.EventBlur;
import xyz.cucumber.base.events.ext.EventRenderGui;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.addons.Dragable;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.cfgs.SettingsUtils;
import xyz.cucumber.base.utils.math.PositionHandler;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.Fonts;

@ModuleInfo(category=Category.OTHER, description="Displays enabled modules", name="Array List", priority=ArrayPriority.LOW)
public class ArrayListModule
extends Mod
implements Dragable {
    public ArrayList<ArrayModule> mods = new ArrayList();
    private NumberSettings positionX = new NumberSettings("Position X", 30.0, 0.0, 1000.0, 1.0);
    private NumberSettings positionY = new NumberSettings("Position Y", 50.0, 0.0, 1000.0, 1.0);
    private BooleanSettings splitNames = new BooleanSettings("Split Names", true);
    private ModeSettings textStyle = new ModeSettings("Text Style", new String[]{"Normal", "Uppercase", "Lowercase"});
    private NumberSettings scale = new NumberSettings("Scale", 1.0, 0.3, 2.0, 0.02);
    private ModeSettings fonts = new ModeSettings("Fonts", SettingsUtils.getFonts());
    private ModeSettings priority = new ModeSettings("Priority", new String[]{"Low", "Medium", "High"});
    private ModeSettings direction = new ModeSettings("Direction", new String[]{"Left Up", "Right Up", "Left Down", "Right Down"});
    private NumberSettings spacing = new NumberSettings("Spacing", 10.0, 8.0, 20.0, 0.2);
    private NumberSettings correction = new NumberSettings("Center corection", 0.0, -5.0, 5.0, 0.02);
    private BooleanSettings rounded = new BooleanSettings("Rounded", true);
    private ColorSettings modColor = new ColorSettings("Text Color", "Static", -1, -1, 100);
    private BooleanSettings shadow = new BooleanSettings("Shadow", true);
    private ColorSettings shadowColor = new ColorSettings("Shadow color", "Static", -1, -1, 100);
    private BooleanSettings suffix = new BooleanSettings("Suffix", true);
    private ModeSettings suffixMode = new ModeSettings("Suffix Mode", new String[]{"None", "[]", "<>", "-"});
    private ColorSettings suffixColor = new ColorSettings("Suffix Color", "Static", -8947849, -1, 100);
    private BooleanSettings blur = new BooleanSettings("Blur", true);
    private BooleanSettings bloom = new BooleanSettings("Bloom", true);
    private ColorSettings bloomColor = new ColorSettings("Bloom Color", "Static", -16777216, -1, 100);
    private ColorSettings backgroundColor = new ColorSettings("Background Color", "Static", -16777216, -1, 100);
    private BooleanSettings outline = new BooleanSettings("Outline", true);
    private NumberSettings size = new NumberSettings("Outline size", 100.0, 0.0, 100.0, 1.0);
    private ColorSettings outlineColor = new ColorSettings("Outline Color", "Static", -16777216, -1, 100);
    private BooleanSettings outlineL = new BooleanSettings("Outline Left", true);
    private BooleanSettings outlineR = new BooleanSettings("Outline Right", true);
    private BooleanSettings outlineTop = new BooleanSettings("Outline Top", true);
    private BooleanSettings outlineBot = new BooleanSettings("Outline Bottom", true);
    private BooleanSettings outlineBetweenBot = new BooleanSettings("Outline Between Bottom", true);

    public ArrayListModule() {
        this.addSettings(this.positionX, this.positionY, this.splitNames, this.textStyle, this.direction, this.spacing, this.correction, this.fonts, this.priority, this.scale, this.rounded, this.modColor, this.shadow, this.shadowColor, this.suffix, this.suffixMode, this.suffixColor, this.blur, this.bloom, this.bloomColor, this.backgroundColor, this.outline, this.size, this.outlineColor, this.outlineL, this.outlineR, this.outlineTop, this.outlineBot, this.outlineBetweenBot);
    }

    @Override
    public void onEnable() {
        double[] pos = PositionHandler.getScaledPosition(this.positionX.getValue(), this.positionY.getValue());
        this.mods.clear();
        for (Mod m : Client.INSTANCE.getModuleManager().getModules()) {
            this.mods.add(new ArrayModule(m, new PositionUtils(pos[0], pos[1], 0.0, 0.0, 1.0f)));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @EventListener
    public void onBloom(EventBloom e) {
        double[] pos = PositionHandler.getScaledPosition(this.positionX.getValue(), this.positionY.getValue());
        if (!this.bloom.isEnabled()) {
            return;
        }
        if (e.getType() == EventType.PRE) {
            e.setCancelled(true);
            return;
        }
        GL11.glPushMatrix();
        GL11.glTranslated((double)(pos[0] - pos[0] * this.scale.getValue()), (double)(pos[1] - pos[1] * this.scale.getValue()), (double)1.0);
        GL11.glScaled((double)this.scale.getValue(), (double)this.scale.getValue(), (double)1.0);
        int i = 0;
        double spacing = this.spacing.getValue();
        block8: for (ArrayModule mod : this.mods) {
            switch (this.priority.getMode().toLowerCase()) {
                case "medium": {
                    if (mod.module.getPriority() != ArrayPriority.LOW) break;
                    continue block8;
                }
                case "high": {
                    if (mod.module.getPriority() != ArrayPriority.HIGH) continue block8;
                }
            }
            if (!mod.module.isEnabled() && mod.animation < 1.0) continue;
            int color = ColorUtils.getColor(this.bloomColor, System.nanoTime() / 1000000L, i * 10, 5.0);
            this.renderBackGround(mod, color);
            ++i;
        }
        GL11.glPopMatrix();
    }

    /*
     * Enabled aggressive block sorting
     */
    @EventListener
    public void onBlur(EventBlur e) {
        double[] pos = PositionHandler.getScaledPosition(this.positionX.getValue(), this.positionY.getValue());
        if (!this.blur.isEnabled()) {
            return;
        }
        if (e.getType() == EventType.PRE) {
            e.setCancelled(true);
            return;
        }
        GL11.glPushMatrix();
        GL11.glTranslated((double)(pos[0] - pos[0] * this.scale.getValue()), (double)(pos[1] - pos[1] * this.scale.getValue()), (double)1.0);
        GL11.glScaled((double)this.scale.getValue(), (double)this.scale.getValue(), (double)1.0);
        block8: for (ArrayModule mod : this.mods) {
            switch (this.priority.getMode().toLowerCase()) {
                case "medium": {
                    if (mod.module.getPriority() != ArrayPriority.LOW) break;
                    continue block8;
                }
                case "high": {
                    if (mod.module.getPriority() != ArrayPriority.HIGH) continue block8;
                }
            }
            if (!mod.module.isEnabled() && mod.animation < 1.0) continue;
            this.renderBackGround(mod, -1);
        }
        GL11.glPopMatrix();
    }

    /*
     * Enabled aggressive block sorting
     */
    @EventListener
    public void onRenderGui(EventRenderGui e) {
        double[] pos = PositionHandler.getScaledPosition(this.positionX.getValue(), this.positionY.getValue());
        this.info = this.direction.getMode();
        Collections.sort(this.mods, new sortBySize());
        GL11.glPushMatrix();
        GL11.glTranslated((double)(pos[0] - pos[0] * this.scale.getValue()), (double)(pos[1] - pos[1] * this.scale.getValue()), (double)1.0);
        GL11.glScaled((double)this.scale.getValue(), (double)this.scale.getValue(), (double)1.0);
        int i = 0;
        block8: for (ArrayModule mod : this.mods) {
            block31: {
                String modname;
                String fullString;
                String font;
                block30: {
                    switch (this.priority.getMode().toLowerCase()) {
                        case "medium": {
                            if (mod.module.getPriority() != ArrayPriority.LOW) break;
                            continue block8;
                        }
                        case "high": {
                            if (mod.module.getPriority() != ArrayPriority.HIGH) continue block8;
                        }
                    }
                    String hor = this.direction.getMode().toLowerCase().split(" ")[0];
                    String ver = this.direction.getMode().toLowerCase().split(" ")[1];
                    font = this.fonts.getMode().toLowerCase();
                    String suffix = this.getSuffix(mod);
                    fullString = String.valueOf(mod.module.getName(this.splitNames.isEnabled())) + suffix;
                    if (this.textStyle.getMode().toLowerCase().equals("lowercase")) {
                        fullString = fullString.toLowerCase();
                    } else if (this.textStyle.getMode().toLowerCase().equals("uppercase")) {
                        fullString = fullString.toUpperCase();
                    }
                    double spacing = this.spacing.getValue();
                    double width = Fonts.getFont(font).getWidth(fullString) + 6.0;
                    mod.position.setWidth(width);
                    mod.position.setHeight(spacing);
                    if (mod.module.isEnabled()) {
                        mod.animation = (mod.animation * 10.0 + width) / 11.0;
                    } else {
                        mod.animation = mod.animation * 10.0 / 11.0;
                        if (mod.animation < 1.0) continue;
                    }
                    if (ver.equals("up")) {
                        mod.position.setY((mod.position.getY() * 10.0 + (pos[1] + spacing * (double)i)) / 11.0);
                    } else {
                        mod.position.setY((mod.position.getY() * 10.0 + (pos[1] - spacing * (double)i)) / 11.0);
                    }
                    if (hor.equals("right")) {
                        mod.position.setX(pos[0] - mod.animation);
                    } else {
                        mod.position.setX(pos[0] + mod.animation - width);
                    }
                    int color = ColorUtils.getColor(this.backgroundColor, System.nanoTime() / 1000000L, i * 10, 5.0);
                    this.renderBackGround(mod, color);
                    this.renderOutlineForMod(mod);
                    modname = mod.module.getName(this.splitNames.isEnabled());
                    String suffixname = suffix;
                    if (this.textStyle.getMode().toLowerCase().equals("lowercase")) {
                        suffixname = suffixname.toLowerCase();
                        modname = modname.toLowerCase();
                    } else if (this.textStyle.getMode().toLowerCase().equals("uppercase")) {
                        suffixname = suffixname.toUpperCase();
                        modname = modname.toUpperCase();
                    }
                    if (mod.module.info.equals("") || mod.module.info == null) break block30;
                    if (hor.equals("right")) {
                        if (this.shadow.isEnabled()) {
                            Fonts.getFont(font).drawString(modname, mod.position.getX() + 3.0 + 0.5 + 0.5, mod.position.getY() + mod.position.getHeight() / 2.0 - (double)(Fonts.getFont(font).getHeight(fullString) / 2.0f) + this.correction.getValue() + 0.5, ColorUtils.getColor(this.shadowColor, System.nanoTime() / 1000000L, i * 10, 5.0));
                            if (!mod.module.info.equals("") || mod.module.info != null) {
                                Fonts.getFont(font).drawString(suffixname, mod.position.getX2() - Fonts.getFont(font).getWidth(suffixname) - 3.0 + 0.5, mod.position.getY() + mod.position.getHeight() / 2.0 - (double)(Fonts.getFont(font).getHeight(fullString) / 2.0f) + this.correction.getValue() + 0.5, ColorUtils.getColor(this.shadowColor, System.nanoTime() / 1000000L, i * 10, 5.0));
                            }
                        }
                        Fonts.getFont(font).drawString(modname, mod.position.getX() + 3.0 + 0.5, mod.position.getY() + mod.position.getHeight() / 2.0 - (double)(Fonts.getFont(font).getHeight(fullString) / 2.0f) + this.correction.getValue(), ColorUtils.getAlphaColor(ColorUtils.getColor(this.modColor, System.nanoTime() / 1000000L, i * 10, 5.0), 100));
                        if (!mod.module.info.equals("") || mod.module.info != null) {
                            Fonts.getFont(font).drawString(suffixname, mod.position.getX2() - Fonts.getFont(font).getWidth(suffixname) - 3.0, mod.position.getY() + mod.position.getHeight() / 2.0 - (double)(Fonts.getFont(font).getHeight(fullString) / 2.0f) + this.correction.getValue(), ColorUtils.getAlphaColor(ColorUtils.getColor(this.suffixColor, System.nanoTime() / 1000000L, i * 10, 5.0), 100));
                        }
                        break block31;
                    } else {
                        if (this.shadow.isEnabled()) {
                            Fonts.getFont(font).drawString(modname, mod.position.getX2() - Fonts.getFont(font).getWidth(modname) - 3.5 + 0.5, mod.position.getY() + mod.position.getHeight() / 2.0 - (double)(Fonts.getFont(font).getHeight(fullString) / 2.0f) + this.correction.getValue() + 0.5, ColorUtils.getColor(this.shadowColor, System.nanoTime() / 1000000L, i * 10, 5.0));
                            if (!mod.module.info.equals("") || mod.module.info != null) {
                                Fonts.getFont(font).drawString(suffixname, mod.position.getX() + 3.0 + 0.5 + 0.5, mod.position.getY() + mod.position.getHeight() / 2.0 - (double)(Fonts.getFont(font).getHeight(fullString) / 2.0f) + this.correction.getValue() + 0.5, ColorUtils.getColor(this.shadowColor, System.nanoTime() / 1000000L, i * 10, 5.0));
                            }
                        }
                        Fonts.getFont(font).drawString(modname, mod.position.getX2() - Fonts.getFont(font).getWidth(modname) - 3.5, mod.position.getY() + mod.position.getHeight() / 2.0 - (double)(Fonts.getFont(font).getHeight(fullString) / 2.0f) + this.correction.getValue(), ColorUtils.getAlphaColor(ColorUtils.getColor(this.modColor, System.nanoTime() / 1000000L, i * 10, 5.0), 100));
                        if (!mod.module.info.equals("") || mod.module.info != null) {
                            Fonts.getFont(font).drawString(suffixname, mod.position.getX() + 3.0 + 0.5, mod.position.getY() + mod.position.getHeight() / 2.0 - (double)(Fonts.getFont(font).getHeight(fullString) / 2.0f) + this.correction.getValue(), ColorUtils.getColor(this.suffixColor, System.nanoTime() / 1000000L, i * 10, 5.0));
                        }
                    }
                    break block31;
                }
                if (this.shadow.isEnabled()) {
                    Fonts.getFont(font).drawString(modname, mod.position.getX() + mod.position.getWidth() / 2.0 - Fonts.getFont(font).getWidth(modname) / 2.0 + 0.5 + 0.5, mod.position.getY() + mod.position.getHeight() / 2.0 - (double)(Fonts.getFont(font).getHeight(fullString) / 2.0f) + this.correction.getValue() + 0.5, ColorUtils.getColor(this.shadowColor, System.nanoTime() / 1000000L, i * 10, 5.0));
                }
                Fonts.getFont(font).drawString(modname, mod.position.getX() + mod.position.getWidth() / 2.0 - Fonts.getFont(font).getWidth(modname) / 2.0 + 0.5, mod.position.getY() + mod.position.getHeight() / 2.0 - (double)(Fonts.getFont(font).getHeight(fullString) / 2.0f) + this.correction.getValue(), ColorUtils.getAlphaColor(ColorUtils.getColor(this.modColor, System.nanoTime() / 1000000L, i * 10, 5.0), 100));
            }
            ++i;
        }
        GL11.glPopMatrix();
    }

    public void renderOutlineForMod(ArrayModule mod) {
        double var1;
        double w;
        if (!this.outline.isEnabled()) {
            return;
        }
        String hor = this.direction.getMode().toLowerCase().split(" ")[0];
        String ver = this.direction.getMode().toLowerCase().split(" ")[1];
        int index = this.getAvalibeModules().indexOf(mod);
        int color = ColorUtils.getColor(this.outlineColor, System.nanoTime() / 1000000L, index * 10, 5.0);
        if (this.outlineL.isEnabled()) {
            w = mod.position.getHeight() * this.size.getValue() / 100.0;
            var1 = (mod.position.getHeight() - w) / 2.0;
            RenderUtils.drawLine(mod.position.getX(), mod.position.getY() + var1, mod.position.getX(), mod.position.getY2() - var1, color, (float)(2.0 * this.scale.getValue()));
        }
        if (this.outlineR.isEnabled()) {
            w = mod.position.getHeight() * this.size.getValue() / 100.0;
            var1 = (mod.position.getHeight() - w) / 2.0;
            RenderUtils.drawLine(mod.position.getX2(), mod.position.getY() + var1, mod.position.getX2(), mod.position.getY2() - var1, color, (float)(2.0 * this.scale.getValue()));
        }
        if (ver.equals("up")) {
            w = mod.position.getWidth() * this.size.getValue() / 100.0;
            var1 = (mod.position.getWidth() - w) / 2.0;
            if (index == 0 && this.outlineTop.isEnabled()) {
                RenderUtils.drawLine(mod.position.getX() + var1, mod.position.getY(), mod.position.getX2() - var1, mod.position.getY(), color, (float)(2.0 * this.scale.getValue()));
            }
            if (this.getAvalibeModules().size() - 1 == index && this.outlineBot.isEnabled()) {
                RenderUtils.drawLine(mod.position.getX() + var1, mod.position.getY2(), mod.position.getX2() - var1, mod.position.getY2(), color, (float)(2.0 * this.scale.getValue()));
            }
        } else {
            w = mod.position.getWidth() * this.size.getValue() / 100.0;
            var1 = (mod.position.getWidth() - w) / 2.0;
            if (index == 0 && this.outlineBot.isEnabled()) {
                RenderUtils.drawLine(mod.position.getX() + var1, mod.position.getY2(), mod.position.getX2() - var1, mod.position.getY2(), color, (float)(2.0 * this.scale.getValue()));
            }
            if (this.getAvalibeModules().size() - 1 == index && this.outlineTop.isEnabled()) {
                RenderUtils.drawLine(mod.position.getX() + var1, mod.position.getY(), mod.position.getX2() - var1, mod.position.getY(), color, (float)(2.0 * this.scale.getValue()));
            }
        }
        if (index != 0 && this.outlineBetweenBot.isEnabled()) {
            double size;
            if (hor.equals("right")) {
                size = this.getAvalibeModules().get((int)(index - 1)).position.getWidth() - mod.position.getWidth();
                if (ver.equals("down")) {
                    RenderUtils.drawLine(mod.position.getX() - size, mod.position.getY2(), mod.position.getX(), mod.position.getY2(), color, (float)(2.0 * this.scale.getValue()));
                } else {
                    RenderUtils.drawLine(mod.position.getX() - size, mod.position.getY(), mod.position.getX(), mod.position.getY(), color, (float)(2.0 * this.scale.getValue()));
                }
            } else {
                size = this.getAvalibeModules().get((int)(index - 1)).position.getWidth() - mod.position.getWidth();
                if (ver.equals("down")) {
                    RenderUtils.drawLine(mod.position.getX2(), mod.position.getY2(), mod.position.getX2() + size, mod.position.getY2(), color, (float)(2.0 * this.scale.getValue()));
                } else {
                    RenderUtils.drawLine(mod.position.getX2(), mod.position.getY(), mod.position.getX2() + size, mod.position.getY(), color, (float)(2.0 * this.scale.getValue()));
                }
            }
        }
    }

    public void renderBackGround(ArrayModule mod, int color) {
        int index = this.getAvalibeModules().indexOf(mod);
        String hor = this.direction.getMode().toLowerCase().split(" ")[0];
        String ver = this.direction.getMode().toLowerCase().split(" ")[1];
        if (!this.rounded.isEnabled()) {
            RenderUtils.drawRect(mod.position.getX(), mod.position.getY(), mod.position.getX2(), mod.position.getY2(), color);
        } else if (index == 0) {
            double size = 2.0;
            boolean leftT = false;
            boolean rightT = false;
            boolean leftB = false;
            boolean rightB = false;
            if (this.getAvalibeModules().size() == 1) {
                leftT = true;
                rightT = true;
                leftB = true;
                rightB = true;
            } else {
                int futureindex = this.getAvalibeModules().indexOf(mod) + 1;
                if (ver.equals("up")) {
                    leftT = true;
                    rightT = true;
                    if (hor.equals("right")) {
                        leftB = true;
                    } else {
                        rightB = true;
                    }
                } else {
                    leftB = true;
                    rightB = true;
                    if (hor.equals("right")) {
                        leftT = true;
                    } else {
                        rightT = true;
                    }
                }
                if (size > Math.abs(this.getAvalibeModules().get((int)futureindex).position.getWidth() - mod.position.getWidth())) {
                    size = Math.abs(this.getAvalibeModules().get((int)futureindex).position.getWidth() - mod.position.getWidth());
                }
            }
            RenderUtils.drawRoundedRectWithCorners(mod.position.getX(), mod.position.getY(), mod.position.getX2(), mod.position.getY2(), color, size, leftT, rightT, leftB, rightB);
        } else if (index == this.getAvalibeModules().indexOf(this.getAvalibeModules().get(this.getAvalibeModules().size() - 1))) {
            boolean leftT = false;
            boolean rightT = false;
            boolean leftB = false;
            boolean rightB = false;
            double size = 2.0;
            if (ver.equals("up")) {
                leftB = true;
                rightB = true;
            } else {
                leftT = true;
                rightT = true;
            }
            RenderUtils.drawRoundedRectWithCorners(mod.position.getX(), mod.position.getY(), mod.position.getX2(), mod.position.getY2(), color, size, leftT, rightT, leftB, rightB);
        } else {
            double size = 2.0;
            boolean leftT = false;
            boolean rightT = false;
            boolean leftB = false;
            boolean rightB = false;
            if (hor.equals("right")) {
                if (ver.equals("up")) {
                    leftB = true;
                } else {
                    leftT = true;
                }
            } else if (ver.equals("up")) {
                rightB = true;
            } else {
                rightT = true;
            }
            int futureindex = this.getAvalibeModules().indexOf(mod) + 1;
            if (size > Math.abs(this.getAvalibeModules().get((int)futureindex).position.getWidth() - mod.position.getWidth())) {
                size = Math.abs(this.getAvalibeModules().get((int)futureindex).position.getWidth() - mod.position.getWidth());
            }
            RenderUtils.drawRoundedRectWithCorners(mod.position.getX(), mod.position.getY(), mod.position.getX2(), mod.position.getY2(), color, size, leftT, rightT, leftB, rightB);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public ArrayList<ArrayModule> getAvalibeModules() {
        ArrayList<ArrayModule> md = new ArrayList<ArrayModule>();
        block8: for (ArrayModule mod : this.mods) {
            switch (this.priority.getMode().toLowerCase()) {
                case "medium": {
                    if (mod.module.getPriority() != ArrayPriority.LOW) break;
                    continue block8;
                }
                case "high": {
                    if (mod.module.getPriority() != ArrayPriority.HIGH) continue block8;
                }
            }
            if (!mod.module.isEnabled() && mod.animation < 1.0) continue;
            md.add(mod);
        }
        return md;
    }

    public String getSuffix(ArrayModule mod) {
        String suffix;
        block16: {
            suffix = "";
            String hor = this.direction.getMode().toLowerCase().split(" ")[0];
            if (!this.suffix.isEnabled() || mod.module.info == null || mod.module.info.equals("")) break block16;
            switch (this.suffixMode.getMode().toLowerCase()) {
                case "none": {
                    suffix = String.valueOf(hor.equals("right") ? " " : "") + mod.module.info + (hor.equals("left") ? " " : "");
                    break;
                }
                case "[]": {
                    suffix = String.valueOf(hor.equals("right") ? " " : "") + "[" + mod.module.info + "]" + (hor.equals("left") ? " " : "");
                    break;
                }
                case "<>": {
                    suffix = String.valueOf(hor.equals("right") ? " " : "") + "<" + mod.module.info + ">" + (hor.equals("left") ? " " : "");
                    break;
                }
                case "-": {
                    suffix = String.valueOf(hor.equals("right") ? " - " : "") + mod.module.info + (hor.equals("left") ? " - " : "");
                }
            }
        }
        return suffix;
    }

    @Override
    public PositionUtils getPosition() {
        double[] pos = PositionHandler.getScaledPosition(this.positionX.getValue(), this.positionY.getValue());
        String hor = this.direction.getMode().toLowerCase().split(" ")[0];
        String ver = this.direction.getMode().toLowerCase().split(" ")[1];
        double vertical = 0.0;
        if (!ver.equals("up")) {
            vertical = this.getAvalibeModules().size() == 0 ? this.spacing.getValue() * this.scale.getValue() : (double)this.getAvalibeModules().size() * this.spacing.getValue() * this.scale.getValue();
        }
        double horizontal = 0.0;
        if (hor.equals("right")) {
            horizontal = this.getAvalibeModules().size() == 0 ? 50.0 : this.getAvalibeModules().get((int)0).position.getWidth();
        }
        return new PositionUtils(pos[0] - horizontal, pos[1] + vertical, this.getAvalibeModules().size() == 0 ? 50.0 : this.getAvalibeModules().get((int)0).position.getWidth(), this.getAvalibeModules().size() == 0 ? this.spacing.getValue() * this.scale.getValue() : (double)this.getAvalibeModules().size() * this.spacing.getValue() * this.scale.getValue(), 1.0f);
    }

    @Override
    public void setXYPosition(double x, double y) {
        String hor = this.direction.getMode().toLowerCase().split(" ")[0];
        String ver = this.direction.getMode().toLowerCase().split(" ")[1];
        double vertical = 0.0;
        if (!ver.equals("up")) {
            vertical = this.getAvalibeModules().size() == 0 ? this.spacing.getValue() * this.scale.getValue() : (double)this.getAvalibeModules().size() * this.spacing.getValue() * this.scale.getValue();
        }
        double horizontal = 0.0;
        if (hor.equals("right")) {
            horizontal = this.getAvalibeModules().size() == 0 ? 50.0 : this.getAvalibeModules().get((int)0).position.getWidth();
        }
        this.positionX.setValue(x + horizontal);
        this.positionY.setValue(y - vertical);
    }

    public class ArrayModule {
        public Mod module;
        public PositionUtils position;
        public double animation;

        public ArrayModule(Mod module, PositionUtils position) {
            this.module = module;
            this.position = position;
        }
    }

    public class sortBySize
    implements Comparator<ArrayModule> {
        @Override
        public int compare(ArrayModule o1, ArrayModule o2) {
            String font = ArrayListModule.this.fonts.getMode().toLowerCase();
            String n1 = String.valueOf(o1.module.getName(ArrayListModule.this.splitNames.isEnabled())) + ArrayListModule.this.getSuffix(o1);
            String n2 = String.valueOf(o2.module.getName(ArrayListModule.this.splitNames.isEnabled())) + ArrayListModule.this.getSuffix(o2);
            if (ArrayListModule.this.textStyle.getMode().toLowerCase().equals("lowercase")) {
                n1 = n1.toLowerCase();
                n2 = n2.toLowerCase();
            } else if (ArrayListModule.this.textStyle.getMode().toLowerCase().equals("uppercase")) {
                n1 = n1.toUpperCase();
                n2 = n2.toUpperCase();
            }
            if (Fonts.getFont(font).getWidth(n1) > Fonts.getFont(font).getWidth(n2)) {
                return -1;
            }
            return 1;
        }
    }
}
