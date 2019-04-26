package com.devsda.platform.shepherdclient.model;

import com.devsda.utils.httputils.constants.Protocol;

public class ServerDetails {

    private Protocol protocol;
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

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = Protocol.getProtocol(protocol);
    }

    @Override
    public String toString() {
        return "ServerDetails{" +
                "protocol=" + protocol +
                ", hostname='" + hostname + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
