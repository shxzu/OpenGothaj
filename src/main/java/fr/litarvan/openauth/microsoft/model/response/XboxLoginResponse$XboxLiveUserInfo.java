package fr.litarvan.openauth.microsoft.model.response;

public class XboxLoginResponse$XboxLiveUserInfo {
    private final String uhs;

    public XboxLoginResponse$XboxLiveUserInfo(String uhs) {
        this.uhs = uhs;
    }

    public String getUserHash() {
        return this.uhs;
    }
}
