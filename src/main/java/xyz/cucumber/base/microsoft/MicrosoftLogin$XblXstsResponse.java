package xyz.cucumber.base.microsoft;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class MicrosoftLogin$XblXstsResponse {
    @Expose
    @SerializedName(value="Token")
    public String Token;
    @Expose
    @SerializedName(value="DisplayClaims")
    public DisplayClaims DisplayClaims;

    private MicrosoftLogin$XblXstsResponse() {
    }

    private static class DisplayClaims {
        @Expose
        @SerializedName(value="xui")
        private Claim[] xui;

        private DisplayClaims() {
        }

        static Claim[] access$0(DisplayClaims displayClaims) {
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
}
