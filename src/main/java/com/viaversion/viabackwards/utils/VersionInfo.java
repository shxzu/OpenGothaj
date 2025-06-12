package com.viaversion.viabackwards.utils;

public final class VersionInfo {
    public static final String VERSION = "4.9.2-SNAPSHOT";
    private static final String IMPLEMENTATION_VERSION = "git-ViaBackwards-4.9.2-SNAPSHOT:2889daed";

    public static String getVersion() {
        return VERSION;
    }

    public static String getImplementationVersion() {
        return IMPLEMENTATION_VERSION;
    }
}
