package net.minecraft.client.gui.stream;

import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public enum GuiStreamUnavailable$Reason {
    NO_FBO(new ChatComponentTranslation("stream.unavailable.no_fbo", new Object[0])),
    LIBRARY_ARCH_MISMATCH(new ChatComponentTranslation("stream.unavailable.library_arch_mismatch", new Object[0])),
    LIBRARY_FAILURE(new ChatComponentTranslation("stream.unavailable.library_failure", new Object[0]), new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0])),
    UNSUPPORTED_OS_WINDOWS(new ChatComponentTranslation("stream.unavailable.not_supported.windows", new Object[0])),
    UNSUPPORTED_OS_MAC(new ChatComponentTranslation("stream.unavailable.not_supported.mac", new Object[0]), new ChatComponentTranslation("stream.unavailable.not_supported.mac.okay", new Object[0])),
    UNSUPPORTED_OS_OTHER(new ChatComponentTranslation("stream.unavailable.not_supported.other", new Object[0])),
    ACCOUNT_NOT_MIGRATED(new ChatComponentTranslation("stream.unavailable.account_not_migrated", new Object[0]), new ChatComponentTranslation("stream.unavailable.account_not_migrated.okay", new Object[0])),
    ACCOUNT_NOT_BOUND(new ChatComponentTranslation("stream.unavailable.account_not_bound", new Object[0]), new ChatComponentTranslation("stream.unavailable.account_not_bound.okay", new Object[0])),
    FAILED_TWITCH_AUTH(new ChatComponentTranslation("stream.unavailable.failed_auth", new Object[0]), new ChatComponentTranslation("stream.unavailable.failed_auth.okay", new Object[0])),
    FAILED_TWITCH_AUTH_ERROR(new ChatComponentTranslation("stream.unavailable.failed_auth_error", new Object[0])),
    INITIALIZATION_FAILURE(new ChatComponentTranslation("stream.unavailable.initialization_failure", new Object[0]), new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0])),
    UNKNOWN(new ChatComponentTranslation("stream.unavailable.unknown", new Object[0]), new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0]));

    private final IChatComponent field_152574_m;
    private final IChatComponent field_152575_n;

    private GuiStreamUnavailable$Reason(IChatComponent p_i1066_3_) {
        this(p_i1066_3_, null);
    }

    private GuiStreamUnavailable$Reason(IChatComponent p_i1067_3_, IChatComponent p_i1067_4_) {
        this.field_152574_m = p_i1067_3_;
        this.field_152575_n = p_i1067_4_;
    }

    public IChatComponent func_152561_a() {
        return this.field_152574_m;
    }

    public IChatComponent func_152559_b() {
        return this.field_152575_n;
    }
}
