package xyz.cucumber.base.microsoft;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class MicrosoftLogin$GameOwnershipResponse$Item {
    @Expose
    @SerializedName(value="name")
    private String name;

    private MicrosoftLogin$GameOwnershipResponse$Item() {
    }

    static String access$0(MicrosoftLogin$GameOwnershipResponse$Item item) {
        return item.name;
    }
}
