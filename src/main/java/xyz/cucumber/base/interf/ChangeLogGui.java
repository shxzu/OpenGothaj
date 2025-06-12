package xyz.cucumber.base.interf;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.render.Fonts;

public class ChangeLogGui
extends GuiScreen {
    private static String file = "https://raw.githubusercontent.com/gothajstorage/gothaj-changelogs/main/Changelogs.json";
    private static List<ChangeLog> changes = new ArrayList<ChangeLog>();

    @Override
    public void initGui() {
        this.reloadChangelogs();
        System.out.println(changes);
    }

    public void reloadChangelogs() {
        if (!changes.isEmpty()) {
            return;
        }
        changes.clear();
        try {
            URLConnection connection = new URL(file).openConnection();
            Throwable throwable = null;
            Object var3_5 = null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));){
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                JsonParser parser = new JsonParser();
                JsonObject json = (JsonObject)parser.parse(stringBuilder.toString());
                this.loadChanges(json);
            }
            catch (Throwable throwable2) {
                if (throwable == null) {
                    throwable = throwable2;
                } else if (throwable != throwable2) {
                    throwable.addSuppressed(throwable2);
                }
                throw throwable;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadChanges(JsonObject json) {
        for (Map.Entry entry : json.entrySet()) {
            JsonArray fixes;
            JsonObject element = (JsonObject)entry.getValue();
            ChangeLog change = new ChangeLog((String)entry.getKey());
            JsonArray adds = element.get("add").getAsJsonArray();
            if (adds != null) {
                int i = 0;
                while (i < adds.size()) {
                    change.adds.add(adds.get(i).getAsString());
                    ++i;
                }
            }
            if ((fixes = element.get("fixes").getAsJsonArray()) != null) {
                int i = 0;
                while (i < fixes.size()) {
                    change.fixes.add(fixes.get(i).getAsString());
                    ++i;
                }
            }
            JsonArray removes = element.get("removes").getAsJsonArray();
            if (fixes != null) {
                int i = 0;
                while (i < removes.size()) {
                    change.removes.add(removes.get(i).getAsString());
                    ++i;
                }
            }
            changes.add(change);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        double h = 0.0;
        for (ChangeLog change : changes) {
            RenderUtils.drawRoundedRect((double)(this.width / 2 - 150), 30.0 + h, (double)(this.width / 2 + 150), (double)(30 + (30 + change.adds.size() * 10 + 24 + change.fixes.size() * 10 + change.removes.size() * 10)) + h, -1879048192, 5.0f);
            GlStateManager.pushMatrix();
            GlStateManager.translate((double)(this.width / 2) - Fonts.getFont("mitr").getWidth("Change log: " + change.version) / 2.0 * (double)1.3f, 34.0 + h, 0.0);
            GlStateManager.scale((double)1.3f, (double)1.3f, 1.0);
            Fonts.getFont("mitr").drawString("Change log: " + change.version, 0.0, 0.0, -1);
            GlStateManager.popMatrix();
            double i = 0.0;
            if (!change.adds.isEmpty()) {
                i += 30.0;
                for (String adds : change.adds) {
                    Fonts.getFont("rb-m").drawString("+ " + adds, this.width / 2 - 140, 34.0 + h + i, -3355444);
                    i += 10.0;
                }
            }
            if (!change.fixes.isEmpty()) {
                i += 12.0;
                for (String fixs : change.fixes) {
                    Fonts.getFont("rb-m").drawString("# " + fixs, this.width / 2 - 140, 34.0 + h + i, -3355444);
                    i += 10.0;
                }
            }
            if (!change.removes.isEmpty()) {
                i += 12.0;
                for (String fixs : change.removes) {
                    Fonts.getFont("rb-m").drawString("- " + fixs, this.width / 2 - 140, 34.0 + h + i, -3355444);
                    i += 10.0;
                }
            }
            h += (double)(30 + change.adds.size() * 10 + 24 + change.fixes.size() * 10 + change.removes.size() * 10 + 8);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public class ChangeLog {
        private String version;
        private List<String> adds = new ArrayList<String>();
        private List<String> fixes = new ArrayList<String>();
        private List<String> removes = new ArrayList<String>();

        public ChangeLog(String version) {
            this.version = version;
        }

        public void updateList(JsonObject json) {
        }

        public String getVersion() {
            return this.version;
        }

        public List<String> getAdds() {
            return this.adds;
        }

        public List<String> getFixes() {
            return this.fixes;
        }

        public List<String> getRemoves() {
            return this.removes;
        }
    }
}
