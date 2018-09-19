package com.devsda.platform.shephardcore.constants;

public class ShephardConstants {

    public static final class Resources {
        public static final String HealthCheck = "/healthCheck";

        public static final String REGISTRATION = "/register";
        public static final String RETRIEVE = "/retrieve";
        public static final String MANAGE = "/manage";

        public static final String CLIENT = "/client";
        public static final String ENDPOINT = "/endpoint";
        public static final String GRAPH_JSON = "/graphJSON";
        public static final String ENDPOINTS = "/endpoints";
        public static final String EXECUTE = "/execute";
    }

    public static final class ServletFilter {

        public static final String CORS = "CORS";
        public static final String ALLOWED_ORIGINS = "allowedOrigins";
        public static final String ALLOWED_HEADERS = "allowedHeaders";
        public static final String ALLOWED_METHODS = "allowedMethods";

        public static final String ALLOWED_ORIGIN_NAMES = "*";
        public static final String ALLOWED_HEADER_NAMES = "X-Requested-With,Content-Type,Accept,Origin";
        public static final String ALLOWED_METHOD_NAMES = "OPTIONS,GET,PUT,POST,DELETE,HEAD";
    }

    public static class DB {
        public static final String MYSQL = "mysql";
    }
    public static final String PROCESS_OWNER = "shepherd-core";
}
