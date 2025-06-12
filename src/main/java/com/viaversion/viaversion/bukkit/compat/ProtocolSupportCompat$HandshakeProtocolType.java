package com.viaversion.viaversion.bukkit.compat;

enum ProtocolSupportCompat$HandshakeProtocolType {
    MAPPED("getProtocolVersion"),
    OBFUSCATED_B("b"),
    OBFUSCATED_C("c");

    private final String methodName;

    private ProtocolSupportCompat$HandshakeProtocolType(String methodName) {
        this.methodName = methodName;
    }

    public String methodName() {
        return this.methodName;
    }
}
