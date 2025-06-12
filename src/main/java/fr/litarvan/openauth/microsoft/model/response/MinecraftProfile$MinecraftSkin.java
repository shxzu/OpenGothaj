package fr.litarvan.openauth.microsoft.model.response;

public class MinecraftProfile$MinecraftSkin {
    private final String id;
    private final String state;
    private final String url;
    private final String variant;
    private final String alias;

    public MinecraftProfile$MinecraftSkin(String id, String state, String url, String variant, String alias) {
        this.id = id;
        this.state = state;
        this.url = url;
        this.variant = variant;
        this.alias = alias;
    }

    public String getId() {
        return this.id;
    }

    public String getState() {
        return this.state;
    }

    public String getUrl() {
        return this.url;
    }

    public String getVariant() {
        return this.variant;
    }

    public String getAlias() {
        return this.alias;
    }
}
