package xyz.cucumber.base.interf;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

public class ChangeLogGui$ChangeLog {
    private String version;
    private List<String> adds = new ArrayList<String>();
    private List<String> fixes = new ArrayList<String>();
    private List<String> removes = new ArrayList<String>();

    public ChangeLogGui$ChangeLog(String version) {
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

    static List access$0(ChangeLogGui$ChangeLog changeLog) {
        return changeLog.adds;
    }

    static List access$1(ChangeLogGui$ChangeLog changeLog) {
        return changeLog.fixes;
    }

    static List access$2(ChangeLogGui$ChangeLog changeLog) {
        return changeLog.removes;
    }

    static String access$3(ChangeLogGui$ChangeLog changeLog) {
        return changeLog.version;
    }
}
