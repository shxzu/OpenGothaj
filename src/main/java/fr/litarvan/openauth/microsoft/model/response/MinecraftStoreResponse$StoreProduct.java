package fr.litarvan.openauth.microsoft.model.response;

public class MinecraftStoreResponse$StoreProduct {
    private final String name;
    private final String signature;

    public MinecraftStoreResponse$StoreProduct(String name, String signature) {
        this.name = name;
        this.signature = signature;
    }

    public String getName() {
        return this.name;
    }

    public String getSignature() {
        return this.signature;
    }
}
