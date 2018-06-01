package com.devsda.platform.shephardcore.application;

import com.devsda.platform.shephardcore.dao.PingDao;
import com.devsda.platform.shephardcore.model.ShephardConfiguration;
import com.devsda.platform.shephardcore.resources.HealthCheckReources;
import com.google.inject.*;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class ShephardApplication extends Application<ShephardConfiguration> {

    public static void main(String[] args) throws Exception {
        new ShephardApplication().run(args);
    }

    public void run(ShephardConfiguration shephardConfiguration, Environment environment) throws Exception {

        Injector injector = createInjector(shephardConfiguration, environment);

        environment.jersey().register(HealthCheckReources.class);
    }

    public Injector createInjector(ShephardConfiguration shephardConfiguration, Environment environment) {

        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(ShephardConfiguration.class).toInstance(new ShephardConfiguration());

                final DBIFactory factory = new DBIFactory();
                final DBI jdbi = factory.build(environment, shephardConfiguration.getDatabase(), "mssql");
                final PingDao dao = jdbi.onDemand(PingDao.class);
                bind(PingDao.class).toInstance(dao);
            }
        });

        return injector;
    }
}
