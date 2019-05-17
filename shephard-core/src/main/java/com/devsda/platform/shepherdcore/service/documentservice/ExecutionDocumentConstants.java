package com.devsda.platform.shepherdcore.service.documentservice;

public class ExecutionDocumentConstants {
    public static final class Fields {
        public static final String EXECUTION_METADATA_FIELD = "executionMetaData";
        public static final String EXECUTION_DATA_FIELD = "executionData";
        public static final String EXCUTION_ID = "executionId";
        public static final String OBJECT_ID = "objectId";
        public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
        public static final String UPDATE_COUNT = "numberOfUpdatesAfterInsertion";
    }

    public static final class Operations{
        public static final String SET_OPERATION = "$set";
        public static final String INCREMENT_OPERATION = "$inc";
    }
}
