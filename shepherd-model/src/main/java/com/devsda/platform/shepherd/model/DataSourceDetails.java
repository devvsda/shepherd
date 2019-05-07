package com.devsda.platform.shepherd.model;

import java.util.ArrayList;

public class DataSourceDetails {

    private String prefix;
    private String user;
    private String password;

    private ArrayList<String> clusters;

    private String path;
    private String params;
    private String dbname;
    private String collectionname;

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getCollectionname() {
        return collectionname;
    }

    public void setCollectionname(String collectionname) {
        this.collectionname = collectionname;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getClusters() {
        return clusters;
    }

    public void setClusters(ArrayList<String> clusters) {
        this.clusters = clusters;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "DataSourceDetails{" +
                "prefix='" + prefix + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", clusters=" + clusters +
                ", path='" + path + '\'' +
                ", params='" + params + '\'' +
                ", dbName='" + dbname + '\'' +
                ", collectionName='" + collectionname + '\'' +
                '}';
    }
}

