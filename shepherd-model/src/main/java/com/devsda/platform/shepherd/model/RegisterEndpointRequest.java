package com.devsda.platform.shepherd.model;

import com.devsda.platform.shepherd.constants.ResourceName;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterEndpointRequest extends ShepherdRequest {

    @JsonProperty("endpointName")
    private String endpointName;

    @JsonProperty("clientName")
    private String clientName;

    @JsonProperty("graphDetails")
    private String DAGGraph;

    @JsonProperty("nodesDetails")
    private String endpointDetails;

    public RegisterEndpointRequest() {
        super(ResourceName.REGISTER_ENDPOINT);
    }

    public String getEndpointName() {
        return endpointName;
    }

    public void setEndpointName(String endpointName) {
        this.endpointName = endpointName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDAGGraph() {
        return DAGGraph;
    }

    public void setDAGGraph(String DAGGraph) {
        this.DAGGraph = DAGGraph;
    }

    public String getEndpointDetails() {
        return endpointDetails;
    }

    public void setEndpointDetails(String endpointDetails) {
        this.endpointDetails = endpointDetails;
    }

    @Override
    public String toString() {
        return "RegisterEndpointRequest{" +
                "endpointName='" + endpointName + '\'' +
                ", clientName='" + clientName + '\'' +
                ", DAGGraph='" + DAGGraph + '\'' +
                ", endpointDetails='" + endpointDetails + '\'' +
                '}';
    }
}
