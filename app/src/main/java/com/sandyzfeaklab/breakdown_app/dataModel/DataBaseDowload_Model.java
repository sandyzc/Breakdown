package com.sandyzfeaklab.breakdown_app.dataModel;

public class DataBaseDowload_Model {


    String url;
    int version;

    public DataBaseDowload_Model(String url, int version, String appVer) {
        this.url = url;
        this.version = version;
        this.appVer = appVer;
    }

    String appVer;


    public String getAppVer() {
        return appVer;
    }

    public void setAppVer(String appVer) {
        this.appVer = appVer;
    }

    public DataBaseDowload_Model() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
