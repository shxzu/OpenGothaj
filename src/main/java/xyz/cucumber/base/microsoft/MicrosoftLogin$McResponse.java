package xyz.cucumber.base.microsoft;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class MicrosoftLogin$McResponse {
    @Expose
    @SerializedName(value="access_token")
    public String access_token;

    private MicrosoftLogin$McResponse() {
    }
}
