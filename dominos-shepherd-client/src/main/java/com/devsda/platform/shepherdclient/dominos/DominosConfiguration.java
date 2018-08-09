package com.devsda.platform.shepherdclient.dominos;

import com.devsda.platform.shepherd.constants.Environment;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.constraints.NotNull;

public class DominosConfiguration extends Configuration {

    @NotNull
    @JsonProperty("environment")
    private Environment environment;

    @NotNull
    @JsonProperty("applicationName")
    private String applicationName;

    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public DataSourceFactory getDatabase() {
        return database;
    }

    public void setDatabase(DataSourceFactory database) {
        this.database = database;
    }

    @Override
    public String toString() {
        return "DominosConfiguration{" +
                "environment=" + environment +
                ", applicationName='" + applicationName + '\'' +
                ", database=" + database +
                '}';
    }
}
