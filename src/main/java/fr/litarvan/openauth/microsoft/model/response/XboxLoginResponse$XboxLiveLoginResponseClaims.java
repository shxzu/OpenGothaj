package fr.litarvan.openauth.microsoft.model.response;

import fr.litarvan.openauth.microsoft.model.response.XboxLoginResponse;

public class XboxLoginResponse$XboxLiveLoginResponseClaims {
    private final XboxLoginResponse.XboxLiveUserInfo[] xui;

    public XboxLoginResponse$XboxLiveLoginResponseClaims(XboxLoginResponse.XboxLiveUserInfo[] xui) {
        this.xui = xui;
    }

    public XboxLoginResponse.XboxLiveUserInfo[] getUsers() {
        return this.xui;
    }
}
