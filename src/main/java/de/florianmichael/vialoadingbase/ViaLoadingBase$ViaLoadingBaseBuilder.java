package de.florianmichael.vialoadingbase;

import com.viaversion.viaversion.ViaManagerImpl;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.libs.gson.JsonObject;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import de.florianmichael.vialoadingbase.model.ComparableProtocolVersion;
import de.florianmichael.vialoadingbase.model.Platform;
import java.io.File;
import java.util.LinkedList;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ViaLoadingBase$ViaLoadingBaseBuilder {
    private final LinkedList<Platform> platforms = new LinkedList();
    private File runDirectory;
    private Integer nativeVersion;
    private BooleanSupplier forceNativeVersionCondition;
    private Supplier<JsonObject> dumpSupplier;
    private Consumer<ViaProviders> providers;
    private Consumer<ViaManagerImpl.ViaManagerBuilder> managerBuilderConsumer;
    private Consumer<ComparableProtocolVersion> onProtocolReload;

    public ViaLoadingBase$ViaLoadingBaseBuilder() {
        this.platforms.add(PSEUDO_VIA_VERSION);
        this.platforms.add(PLATFORM_VIA_BACKWARDS);
        this.platforms.add(PLATFORM_VIA_REWIND);
    }

    public static ViaLoadingBase$ViaLoadingBaseBuilder create() {
        return new ViaLoadingBase$ViaLoadingBaseBuilder();
    }

    public ViaLoadingBase$ViaLoadingBaseBuilder platform(Platform platform) {
        this.platforms.add(platform);
        return this;
    }

    public ViaLoadingBase$ViaLoadingBaseBuilder platform(Platform platform, int position) {
        this.platforms.add(position, platform);
        return this;
    }

    public ViaLoadingBase$ViaLoadingBaseBuilder runDirectory(File runDirectory) {
        this.runDirectory = runDirectory;
        return this;
    }

    public ViaLoadingBase$ViaLoadingBaseBuilder nativeVersion(int nativeVersion) {
        this.nativeVersion = nativeVersion;
        return this;
    }

    public ViaLoadingBase$ViaLoadingBaseBuilder forceNativeVersionCondition(BooleanSupplier forceNativeVersionCondition) {
        this.forceNativeVersionCondition = forceNativeVersionCondition;
        return this;
    }

    public ViaLoadingBase$ViaLoadingBaseBuilder dumpSupplier(Supplier<JsonObject> dumpSupplier) {
        this.dumpSupplier = dumpSupplier;
        return this;
    }

    public ViaLoadingBase$ViaLoadingBaseBuilder providers(Consumer<ViaProviders> providers) {
        this.providers = providers;
        return this;
    }

    public ViaLoadingBase$ViaLoadingBaseBuilder managerBuilderConsumer(Consumer<ViaManagerImpl.ViaManagerBuilder> managerBuilderConsumer) {
        this.managerBuilderConsumer = managerBuilderConsumer;
        return this;
    }

    public ViaLoadingBase$ViaLoadingBaseBuilder onProtocolReload(Consumer<ComparableProtocolVersion> onProtocolReload) {
        this.onProtocolReload = onProtocolReload;
        return this;
    }

    public void build() {
        if (ViaLoadingBase.getInstance() != null) {
            LOGGER.severe("ViaLoadingBase has already started the platform!");
            return;
        }
        if (this.runDirectory == null || this.nativeVersion == null) {
            LOGGER.severe("Please check your ViaLoadingBaseBuilder arguments!");
            return;
        }
        new ViaLoadingBase(this.platforms, this.runDirectory, this.nativeVersion, this.forceNativeVersionCondition, this.dumpSupplier, this.providers, this.managerBuilderConsumer, this.onProtocolReload);
    }
}
