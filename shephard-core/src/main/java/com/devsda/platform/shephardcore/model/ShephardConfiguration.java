package com.devsda.platform.shephardcore.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ShephardConfiguration extends Configuration implements Serializable {

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

    public void setEnvironment(String environment) {
        this.environment = Environment.valueOf(environment);
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
        return "ShephardConfiguration{" +
                "environment=" + environment +
                ", applicationName='" + applicationName + '\'' +
                ", database=" + database +
                '}';
    }
}
