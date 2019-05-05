package com.devsda.platform.shepherdclient.impl;

import java.util.Date;

public class DocumentInputClass {

    private String name;
    private String payLoad;
    private PayLoad payload;

    public PayLoad getPayload() {
        return payload;
    }

    public void setPayload(PayLoad payload) {
        this.payload = payload;
    }

    public DocumentInputClass(String name, String payLoad, PayLoad payload) {
        this.name = name;
        this.payLoad = payLoad;
        this.payload = payload;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DocumentInputClass() {}

    public String getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(String payLoad) {
        this.payLoad = payLoad;
    }

    @Override
    public String toString() {
        return "DocumentInputClass{" +
                "name='" + name + '\'' +
                ", payLoad='" + payLoad + '\'' +
                '}';
    }
}