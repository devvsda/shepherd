package com.devsda.platform.shepherdclient.impl;

import com.devsda.platform.shepherdclient.constants.Environment;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShepherdClientTest {

    private static ShepherdClient shepherdClient;

    @BeforeClass
    public static void setUp() throws Exception {
        shepherdClient = new ShepherdClient(Environment.DEV);
    }

    @AfterClass
    public static void tearDown() {

    }

    @Test
    public void registerClientTest() {

        shepherdClient.registerClient("hitesh_dev");

    }
}
