package com.devsda.platform.shepherdcore.model;

public class HealthCheck {

    private String echoText;

    public String getEchoText() {
        return echoText;
    }

    public void setEchoText(String echoText) {
        this.echoText = echoText;
    }

    @Override
    public String toString() {
        return "HealthCheck{" +
                "echoText='" + echoText + '\'' +
                '}';
    }
}
