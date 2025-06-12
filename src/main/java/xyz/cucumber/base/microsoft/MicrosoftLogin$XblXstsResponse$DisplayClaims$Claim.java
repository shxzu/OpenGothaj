package xyz.cucumber.base.microsoft;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class MicrosoftLogin$XblXstsResponse$DisplayClaims$Claim {
    @Expose
    @SerializedName(value="uhs")
    private String uhs;

    private MicrosoftLogin$XblXstsResponse$DisplayClaims$Claim() {
    }

    static String access$0(MicrosoftLogin$XblXstsResponse$DisplayClaims$Claim claim) {
        return claim.uhs;
    }
}
