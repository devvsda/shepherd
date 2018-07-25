package com.devsda.platform.shephardcore.model;

public class ServerDetails {

    private String hostName;
    private String port;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "ServerDetails{" +
                "hostName='" + hostName + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
