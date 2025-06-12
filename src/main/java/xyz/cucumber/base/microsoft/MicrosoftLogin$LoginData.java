package xyz.cucumber.base.microsoft;

public class MicrosoftLogin$LoginData {
    public String mcToken;
    public String newRefreshToken;
    public String uuid;
    public String username;

    public MicrosoftLogin$LoginData() {
    }

    public MicrosoftLogin$LoginData(String mcToken, String newRefreshToken, String uuid, String username) {
        this.mcToken = mcToken;
        this.newRefreshToken = newRefreshToken;
        this.uuid = uuid;
        this.username = username;
    }

    public boolean isGood() {
        return this.mcToken != null;
    }
}
