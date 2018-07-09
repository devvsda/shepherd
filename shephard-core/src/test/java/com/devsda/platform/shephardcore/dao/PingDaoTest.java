package com.devsda.platform.shephardcore.dao;

import com.devsda.platform.shephardcore.ApplicationContextUtil;
import com.google.inject.Injector;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PingDaoTest {

    private static final Logger log = LoggerFactory.getLogger(PingDaoTest.class);
    private static PingDao pingDao;

    @BeforeClass
    public static void setUp() throws IOException {
        Injector injector = ApplicationContextUtil.createApplicationInjector();
        pingDao = injector.getInstance(PingDao.class);
    }

    @Ignore
    @Test
    public void notNullTest() {
        Assert.assertNotNull(pingDao);
    }

    @Ignore
    @Test
    public void pingTest() {
        int response = pingDao.ping();
        System.out.println(response);
    }

}
