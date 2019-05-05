package com.devsda.platform.shepherdclient.impl;

import java.util.Date;

public class PayLoad {
    private String field1;
    private Date field2;

    public PayLoad(String field1, Date field2, boolean field3, int[] field4) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
    }

    private boolean field3;
    private int [] field4;

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public Date getField2() {
        return field2;
    }

    public void setField2(Date field2) {
        this.field2 = field2;
    }

    public boolean isField3() {
        return field3;
    }

    public void setField3(boolean field3) {
        this.field3 = field3;
    }

    public int[] getField4() {
        return field4;
    }

    public void setField4(int[] field4) {
        this.field4 = field4;
    }
}
