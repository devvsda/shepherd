package com.devsda.platform.shepherdclient.constants;

public class ShepherdClientConstants {

    public static final String CONFIGURATION_FILE_NAME = "configurations/" + PlaceHolders.Environment + "-shepherd-configuration.yaml";

    public static class PlaceHolders {
        public static final String Environment = "{env}";
    }

    public static final class Resources {
        public static final String REGISTER_CLIENT = "/shephard-core/register/client";
        public static final String REGISTER_ENDPOINT = "/shephard-core/register/endpoint";
        public static final String UPDATE_WORKFLOW_DETAILS = "/shephard-core/update/endpoint/workflowDetails";
        public static final String UPDATE_ENDPOINT_DETAILS = "/shephard-core/update/endpoint/endpointDetails";
        public static final String RETRIEVE_ENDPOINT = "/shephard-core/retrieve/endpoint";
        public static final String EXECUTE_ENDPOINT = "/shephard-core/execute/endpoint";
        public static final String GET_EXECUTION_STATE = "/shephard-core/retrieve/executionState";
    }
}
