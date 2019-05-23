package com.devsda.platform.shepherdcore.application;

import com.devsda.platform.shepherdcore.constants.ShephardConstants;
import com.devsda.platform.shepherdcore.consumer.NodeExecutorCommand;
import com.devsda.platform.shepherdcore.dao.RegisterationDao;
import com.devsda.platform.shepherdcore.dao.WorkflowOperationDao;
import com.devsda.platform.shepherdcore.model.ShepherdConfiguration;
import com.devsda.platform.shepherdcore.resources.*;
import com.devsda.platform.shepherdcore.service.ExecuteWorkflowServiceHelper;
import com.devsda.platform.shepherdcore.service.queueservice.ConnectionType;
import com.devsda.platform.shepherdcore.service.queueservice.RabbitMQOperation;
import com.devsda.platform.shepherdcore.util.RequestValidator;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.rabbitmq.client.Connection;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.EnumSet;
import java.util.concurrent.TimeoutException;


/**
 * This is a Shepherd Application Context class. Entry point of Shepherd-core.
 */
public class ShepherdApplication extends Application<ShepherdConfiguration> {

    private static final Logger log = LoggerFactory.getLogger(ShepherdApplication.class);

    public static void main(String[] args) throws Exception {
        new ShepherdApplication().run(args);
    }

    /**
     * This is a main run method to start Shepherd application
     *
     * @param shepherdConfiguration
     * @param environment
     * @throws Exception
     */
    public void run(ShepherdConfiguration shepherdConfiguration, Environment environment) throws Exception {

        Injector injector = createInjector(shepherdConfiguration, environment);

        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter(ShephardConstants.ServletFilter.CORS, CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(ShephardConstants.ServletFilter.ALLOWED_ORIGINS, ShephardConstants.ServletFilter.ALLOWED_ORIGIN_NAMES);
        cors.setInitParameter(ShephardConstants.ServletFilter.ALLOWED_HEADERS, ShephardConstants.ServletFilter.ALLOWED_HEADER_NAMES);
        cors.setInitParameter(ShephardConstants.ServletFilter.ALLOWED_METHODS, ShephardConstants.ServletFilter.ALLOWED_METHOD_NAMES);

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        environment.jersey().register(HealthCheckReources.class);
        environment.jersey().register(injector.getInstance(ClientRegisterationResources.class));
        environment.jersey().register(injector.getInstance(ClientDataRetrievalResources.class));
        environment.jersey().register(injector.getInstance(ExecuteWorkflowResources.class));
        environment.jersey().register(injector.getInstance(ClientUpdateInformationResources.class));
        environment.jersey().register(injector.getInstance(WorkflowManagementResources.class));
    }

    private Connection initializeReadyToExecuteQueueProducerConnection(String rabbitMQServerURL, RabbitMQOperation rabbitMQOperation)
            throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException{
        Connection publisherConnection = rabbitMQOperation.createQueueConnection(rabbitMQServerURL, ConnectionType.publisher);
        return publisherConnection;
    }

    private Connection initializeReadyToExecuteQueueConsumerConnection(String rabbitMQServerURL, RabbitMQOperation rabbitMQOperation)
            throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException{
        Connection consumerConnection = rabbitMQOperation.createQueueConnection(rabbitMQServerURL, ConnectionType.consumer);
        return consumerConnection;
    }

    /**
     * This method creates a injector
     *
     * @param shepherdConfiguration
     * @param environment
     * @return
     */
    public Injector createInjector(ShepherdConfiguration shepherdConfiguration, Environment environment) {

        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {

                try {
                    // Binding DB layer with Application layer.
                    final DBIFactory factory = new DBIFactory();
                    final DBI jdbi = factory.build(environment, shepherdConfiguration.getDatabase(), ShephardConstants.DB.MYSQL);
                    final RegisterationDao registerationDao = jdbi.onDemand(RegisterationDao.class);
                    final WorkflowOperationDao workflowOperationDao = jdbi.onDemand(WorkflowOperationDao.class);

                    bind(RegisterationDao.class).toInstance(registerationDao);
                    bind(WorkflowOperationDao.class).toInstance(workflowOperationDao);

                    bind(Connection.class).annotatedWith(Names.named(ShephardConstants.RabbitMQ.PUBLISHER))
                            .toInstance(initializeReadyToExecuteQueueProducerConnection(
                                    shepherdConfiguration.getRabbitMQServerUrl(), new RabbitMQOperation()));
                    bind(Connection.class).annotatedWith(Names.named(ShephardConstants.RabbitMQ.CONSUMER))
                            .toInstance(initializeReadyToExecuteQueueConsumerConnection(
                                    shepherdConfiguration.getRabbitMQServerUrl(), new RabbitMQOperation()));


                    requestStaticInjection(ExecuteWorkflowServiceHelper.class);
                    requestStaticInjection(RequestValidator.class);

                    // Other objects
                    bind(ShepherdConfiguration.class).toInstance(shepherdConfiguration);
                } catch (Exception  e) {
                    log.error("Failed to create DI tree", e);
                }
            }
        });

        return injector;
    }

    @Override
    public void initialize(Bootstrap<ShepherdConfiguration> bootstrap) {
        bootstrap.addCommand(new NodeExecutorCommand());
    }
}
