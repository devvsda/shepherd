package com.devsda.platform.shepherd.model;

import com.devsda.utils.httputils.constants.Protocol;

public class ServerDetails {

    private Protocol protocol;
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
                ", hostName='" + hostName + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}
