package xyz.cucumber.base.microsoft;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class MicrosoftLogin$ProfileResponse {
    @Expose
    @SerializedName(value="id")
    public String id;
    @Expose
    @SerializedName(value="name")
    public String name;

    private MicrosoftLogin$ProfileResponse() {
    }
}
