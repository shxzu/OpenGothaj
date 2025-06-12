package xyz.cucumber.base.microsoft;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class MicrosoftLogin$XblXstsResponse$DisplayClaims {
    @Expose
    @SerializedName(value="xui")
    private Claim[] xui;

    private MicrosoftLogin$XblXstsResponse$DisplayClaims() {
    }

    static Claim[] access$0(MicrosoftLogin$XblXstsResponse$DisplayClaims displayClaims) {
        return displayClaims.xui;
    }

    private static class Claim {
        @Expose
        @SerializedName(value="uhs")
        private String uhs;

        private Claim() {
        }

        static String access$0(Claim claim) {
            return claim.uhs;
        }
    }
}
