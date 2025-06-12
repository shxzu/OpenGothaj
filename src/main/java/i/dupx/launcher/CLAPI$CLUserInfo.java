package i.dupx.launcher;

import i.dupx.launcher.CLAPI;
import java.io.DataInputStream;
import java.io.IOException;

public class CLAPI$CLUserInfo {
    public final String nickname;
    public final int role;
    public final String runningClient;
    public final String runningBranch;
    public final String mc_name;
    private long expire;

    private CLAPI$CLUserInfo(DataInputStream dis) throws IOException {
        if (dis.readBoolean()) {
            this.nickname = CLAPI.readString(dis);
            this.role = dis.readUnsignedByte();
            this.runningClient = CLAPI.readString(dis);
            this.runningBranch = CLAPI.readString(dis);
            this.mc_name = CLAPI.readString(dis);
            this.expire = System.currentTimeMillis() + 60000L;
        } else {
            this.nickname = "None";
            this.role = Integer.MAX_VALUE;
            this.runningClient = "-";
            this.runningBranch = "-";
            this.mc_name = "-";
        }
    }

    private CLAPI$CLUserInfo() {
        this.nickname = "Loading...";
        this.role = Integer.MAX_VALUE;
        this.runningClient = "None";
        this.runningBranch = "None";
        this.expire = Long.MAX_VALUE;
        this.mc_name = "Unknown";
    }

    public String toString() {
        return "nickname=" + this.nickname + "," + "role=" + this.role + "," + "runningClient=" + this.runningClient + "," + "runningBranch=" + this.runningBranch;
    }

    static long access$1(CLAPI$CLUserInfo cLUserInfo) {
        return cLUserInfo.expire;
    }
}
