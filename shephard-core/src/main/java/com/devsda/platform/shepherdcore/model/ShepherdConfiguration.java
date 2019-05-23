package com.devsda.platform.shepherdcore.model;

import com.devsda.platform.shepherd.model.DataSourceDetails;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ShepherdConfiguration extends Configuration implements Serializable {

    @NotNull
    @JsonProperty("environment")
    private Environment environment;

    @NotNull
    @JsonProperty("applicationName")
    private String applicationName;

    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    @NotNull
    @JsonProperty("datasource")
    private DataSourceDetails dataSourceDetails;

    @NotNull
    @JsonProperty("rabbitMQserverUrl")
    private String rabbitMQServerUrl;

    @NotNull
    @JsonProperty("")
    private String readyToExecuteQueue;



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

    public DataSourceDetails getDataSourceDetails() {
        return dataSourceDetails;
    }

    public void setDataSourceDetails(DataSourceDetails dataSourceDetails) {
        this.dataSourceDetails = dataSourceDetails;
    }

    public String getRabbitMQServerUrl() {
        return rabbitMQServerUrl;
    }

    public void setRabbitMQServerUrl(String rabbitMQServerUrl) {
        this.rabbitMQServerUrl = rabbitMQServerUrl;
    }

    @Override
    public String toString() {
        return "ShepherdConfiguration{" +
                "environment=" + environment +
                ", applicationName='" + applicationName + '\'' +
                ", database=" + database +
                ", dataSourceDetails=" + dataSourceDetails +
                ", rabbitMQServerUrl='" + rabbitMQServerUrl + '\'' +
                '}';
    }
}
