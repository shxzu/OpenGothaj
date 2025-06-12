package xyz.cucumber.base;

import i.dupx.launcher.CLAPI;
import xyz.cucumber.base.utils.cfgs.ConfigFileUtils;

class Client$1
implements CLAPI.IClient {
    Client$1() {
    }

    @Override
    public String writeCurrentConfig() {
        return ConfigFileUtils.getString(ConfigFileUtils.file);
    }

    @Override
    public void loadCurrentConfig(String data) {
        ConfigFileUtils.load("CL Config", data, true);
    }

    @Override
    public void joinServer(String data) {
        System.out.println("joining server: " + data);
    }
}
