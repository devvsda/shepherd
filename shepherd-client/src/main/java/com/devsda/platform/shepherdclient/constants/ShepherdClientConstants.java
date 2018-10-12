package com.devsda.platform.shepherdclient.constants;

public class ShepherdClientConstants {

    public static class PlaceHolders {
        public static final String Environment = "{env}";
    }

    public static final String CONFIGURATION_FILE_NAME = "configurations/" + PlaceHolders.Environment + "-shepherd-configuration.yaml";

    public static final class Resources {
        public static final String REGISTER_CLIENT = "/shephard-core/register/client";
        public static final String REGISTER_ENDPOINT = "/shephard-core/register/endpoint";
        public static final String RETRIEVE_ENDPOINT = "/shephard-core/retrieve/endpoint";
        public static final String EXECUTE_ENDPOINT = "/shephard-core/manage/execute";
    }
}
