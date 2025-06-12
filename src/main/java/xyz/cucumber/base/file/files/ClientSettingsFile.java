package xyz.cucumber.base.file.files;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Map;
import xyz.cucumber.base.Client;
import xyz.cucumber.base.interf.clientsettings.ext.Setting;
import xyz.cucumber.base.interf.clientsettings.ext.adds.BloomSetting;
import xyz.cucumber.base.interf.clientsettings.ext.adds.BlurSetting;
import xyz.cucumber.base.utils.FileUtils;

public class ClientSettingsFile
extends FileUtils {
    public ClientSettingsFile() {
        super("Gothaj", "client-settings.json");
    }

    @Override
    public void save() {
        try {
            JsonObject json = new JsonObject();
            for (Setting s : Client.INSTANCE.getClientSettings().getSettings()) {
                JsonObject jsonMod = new JsonObject();
                if (s instanceof BloomSetting) {
                    jsonMod.addProperty("compression", (Number)((BloomSetting)s).compression.getValue());
                    jsonMod.addProperty("radius", (Number)((BloomSetting)s).radius.getValue());
                    jsonMod.addProperty("saturation", (Number)((BloomSetting)s).saturation.getValue());
                }
                if (s instanceof BlurSetting) {
                    jsonMod.addProperty("radius", (Number)((BlurSetting)s).radius.getValue());
                }
                json.add(s.getName(), (JsonElement)jsonMod);
            }
            PrintWriter save = new PrintWriter(new FileWriter(this.getFile()));
            save.println(prettyGson.toJson((JsonElement)json));
            save.close();
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public void load() {
        try {
            BufferedReader load = new BufferedReader(new FileReader(this.getFile()));
            JsonObject json = (JsonObject)jsonParser.parse((Reader)load);
            load.close();
            for (Map.Entry entry : json.entrySet()) {
                JsonObject module = (JsonObject)entry.getValue();
                Setting settingName = Client.INSTANCE.getClientSettings().getSettingByName((String)entry.getKey());
                if (settingName == null) continue;
                for (Setting s : Client.INSTANCE.getClientSettings().getSettings()) {
                    Setting setting;
                    if (s instanceof BloomSetting) {
                        setting = (BloomSetting)s;
                        setting.compression.setValue(module.get("compression").getAsInt());
                        setting.radius.setValue(module.get("radius").getAsInt());
                        setting.saturation.setValue(module.get("saturation").getAsInt());
                    }
                    if (!(s instanceof BlurSetting)) continue;
                    setting = (BlurSetting)s;
                    ((BlurSetting)setting).radius.setValue(module.get("radius").getAsInt());
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}
