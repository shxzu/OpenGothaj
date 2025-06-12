package xyz.cucumber.base.microsoft;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class MicrosoftLogin$AuthTokenResponse {
    @Expose
    @SerializedName(value="access_token")
    public String access_token;
    @Expose
    @SerializedName(value="refresh_token")
    public String refresh_token;

    private MicrosoftLogin$AuthTokenResponse() {
    }
}
