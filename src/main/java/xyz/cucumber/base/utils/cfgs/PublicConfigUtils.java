package xyz.cucumber.base.utils.cfgs;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class PublicConfigUtils {
    private static final String configs = "Configs.txt";
    private static final String repo = "https://raw.githubusercontent.com/ScRichard/GothajPublicConfigs/main/";
    private static HashMap<String, String> publicConfigs = new HashMap();

    public static void reload() {
        String[] files;
        String[] stringArray = files = PublicConfigUtils.loadNames();
        int n = files.length;
        int n2 = 0;
        while (n2 < n) {
            String file = stringArray[n2];
            publicConfigs.put(file, PublicConfigUtils.scrapeUrl(repo + file + ".json"));
            ++n2;
        }
        System.out.println(publicConfigs.size());
    }

    private static String[] loadNames() {
        try {
            System.out.println(PublicConfigUtils.scrapeUrl("https://raw.githubusercontent.com/ScRichard/GothajPublicConfigs/main/Configs.txt"));
            return PublicConfigUtils.scrapeUrl("https://raw.githubusercontent.com/ScRichard/GothajPublicConfigs/main/Configs.txt").split("-");
        }
        catch (Exception ex) {
            return new String[]{"no internet"};
        }
    }

    private static String scrapeUrl(String ur) {
        try {
            URL url = new URL(ur);
            Scanner scan = new Scanner(url.openStream());
            String currContent = new String();
            while (scan.hasNext()) {
                currContent = String.valueOf(currContent) + scan.nextLine();
            }
            scan.close();
            return currContent;
        }
        catch (IOException ex) {
            return null;
        }
    }

    public static String getConfigs() {
        return configs;
    }

    public static String getRepo() {
        return repo;
    }

    public static HashMap<String, String> getPublicConfigs() {
        return publicConfigs;
    }

    public static void setPublicConfigs(HashMap<String, String> publicConfigs) {
        PublicConfigUtils.publicConfigs = publicConfigs;
    }
}
