package com.devsda.platform.shepherdclient.model;

public class ServerDetails {

    private String hostname;
    private String port;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
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
                "hostname='" + hostname + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
