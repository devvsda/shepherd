package com.devsda.platform.shephardcore.service.documentservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ExecutionDetailsMetaData implements Serializable {
    private String executionId;

    public ExecutionDetailsMetaData(String executionId, Integer clientId, Date executionStartDateTime) {
        this.executionId = executionId;
        this.clientId = clientId;
        this.executionStartDateTime = executionStartDateTime;
    }

    private Integer clientId;
    private Date executionStartDateTime;

    @Override
    public String toString() {
        return "ExecutionDetailsMetaData{" +
                "clientId=" + clientId +
                ", executionStartDateTime=" + executionStartDateTime +
                ", numberOfUpdatesAfterInsertion=" + numberOfUpdatesAfterInsertion +
                ", listOfNodesMadeUpdateOnDocument=" + listOfNodesMadeUpdateOnDocument +
                '}';
    }

    private int numberOfUpdatesAfterInsertion;
    private ArrayList<String> listOfNodesMadeUpdateOnDocument;

    public ExecutionDetailsMetaData() {
    }

    public ExecutionDetailsMetaData(String executionId) {
        this.executionId = executionId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Date getExecutionStartDateTime() {
        return executionStartDateTime;
    }

    public void setExecutionStartDateTime(Date executionStartDateTime) {
        this.executionStartDateTime = executionStartDateTime;
    }

    public int getNumberOfUpdatesAfterInsertion() {
        return numberOfUpdatesAfterInsertion;
    }

    public void setNumberOfUpdatesAfterInsertion(int numberOfUpdatesAfterInsertion) {
        this.numberOfUpdatesAfterInsertion = numberOfUpdatesAfterInsertion;
    }

    public ArrayList<String> getListOfNodesMadeUpdateOnDocument() {
        return listOfNodesMadeUpdateOnDocument;
    }

    public void setListOfNodesMadeUpdateOnDocument(ArrayList<String> listOfNodesMadeUpdateOnDocument) {
        this.listOfNodesMadeUpdateOnDocument = listOfNodesMadeUpdateOnDocument;
    }
}
