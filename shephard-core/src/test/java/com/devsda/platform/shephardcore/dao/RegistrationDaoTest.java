package com.devsda.platform.shephardcore.dao;

import com.devsda.platform.shephardcore.ApplicationContextUtil;
import com.devsda.platform.shephardcore.constants.ShephardConstants;
import com.devsda.platform.shephardcore.model.ClientDetails;
import com.devsda.platform.shephardcore.model.EndpointDetails;
import com.devsda.platform.shepherd.util.DateUtil;
import com.google.inject.Injector;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class RegistrationDaoTest {

    private static RegisterationDao registerationDao;

    @BeforeClass
    public static void setup() throws Exception {
        Injector injector = ApplicationContextUtil.createApplicationInjector();
        registerationDao = injector.getInstance(RegisterationDao.class);
    }

    @AfterClass
    public static void tearDown() {

    }

    @Test
    public void checkObjectTest() {
        Assert.assertNotNull(registerationDao);
    }

    @Test
    public void registerClientTest() {

        ClientDetails clientDetails = new ClientDetails("hitesh_dev", ShephardConstants.PROCESS_OWNER);
        clientDetails.setCreatedAt(DateUtil.currentDate());
        clientDetails.setUpdatedAt(DateUtil.currentDate());

        registerationDao.registerClient(clientDetails);
    }

    @Test
    public void getClientDetailsTest() {

        System.out.println(registerationDao.getClientDetails("hitesh_dev_1"));
    }

    @Test
    public void getAllClientDetailsTest() {
        List<ClientDetails> clientDetails = registerationDao.getAllClientDetails();

        System.out.println(clientDetails);
    }

    @Test
    public void registerEndpointTest() {

        EndpointDetails endpointDetails = new EndpointDetails();
        endpointDetails.setEndpointName("random_endpoint");
        endpointDetails.setClientName("hitesh_dev");
        endpointDetails.setDAGGraph("graph");
        endpointDetails.setEndpointDetails("nodes_details");
        endpointDetails.setCreatedAt(DateUtil.currentDate());
        endpointDetails.setUpdatedAt(DateUtil.currentDate());
        endpointDetails.setSubmittedBy(ShephardConstants.PROCESS_OWNER);

        registerationDao.registerEndpoint(endpointDetails);
    }

    @Test
    public void getEndpointDetailsTest() {

        System.out.println(registerationDao.getEndpointDetails(1, "random_endpoint"));

    }

    @Test
    public void getAllEndpointsTest() {

        System.out.println(registerationDao.getAllEndpoints(29));

    }

}
