package com.devsda.platform.shepherd.model;

import com.devsda.utils.httputils.constants.Protocol;

import java.util.ArrayList;

public class DataSourceDetails {

    private String prefix;
    private String user;
    private String password;

    private ArrayList<String> clusters;

    private String path;
    private String params;

    public String getDb_name() {
        return db_name;
    }

    public void setDb_name(String db_name) {
        this.db_name = db_name;
    }

    public String getCollection_name() {
        return collection_name;
    }

    public void setCollection_name(String collection_name) {
        this.collection_name = collection_name;
    }

    private String db_name;
    private String collection_name;


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
                ", db_name='" + db_name + '\'' +
                ", collection_name='" + collection_name + '\'' +
                '}';
    }
}

