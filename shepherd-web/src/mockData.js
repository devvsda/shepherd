import clientsData from './service/clients.json';
import endpoints from './service/endPoints.json';
import executions from './service/executions.json';
import attempt1 from './service/attempt1.json';

import { fetchFromLocalstorage, setToLocalstorage } from './utils/util';
import { addClient, fetchClients, fetchEndPoints, createEndPoint } from './service/service';

export const clientsAPI = {
    clients: fetchFromLocalstorage('clients') || clientsData,
    all: function(cb) {
        fetchClients(cb);
    },
    get: function (id) {
        const isClient = client => client.clientId === id;
        return this.clients.find(isClient);
    },
    add: function(clientName, cb) {
        addClient(clientName, cb);
    }
};

export const endPointsAPI = {
    endpoints: fetchFromLocalstorage('endpoints') || endpoints,
    all: function() { return this.endPoints },
    get: function (clientName, cb) {
        //const isEndPoint = endPoint => endPoint.clientId === clientId;
        //return this.endPoints.filter(isEndPoint);
        fetchEndPoints(clientName, cb);
    },
    add: function(clientName, endpointName, xmlData, jsonData, cb) {
        // this.endPoints.push(endpoint);
        // setToLocalstorage('endpoints', this.endPoints);
        createEndPoint(clientName, endpointName, xmlData, jsonData, cb);
    },
    getEndPointById: function(endpointId) {
        const isEndpoint = endpoint => endpoint.endpointId === endpointId;
        return this.endpoints.find(isEndpoint);
    }
};

export const attemptsAPI = {
    attempts: fetchFromLocalstorage('attempts') || [
        {...attempt1}
    ],
    all: function() { return this.attempts },
    get: function (id) {
        const isAttempt = attempt => attempt.executionId === id;
        return this.attempts.filter(isAttempt);
    },
    add: function(attempt) {
        this.attempts.push(attempt);
        setToLocalstorage('attempts', this.attempts);
    }
};

export const executionAPI = {
    executions: fetchFromLocalstorage('executions') || executions,
    all: function() { return this.executions },
    get: function (endpointName) {
        const isExecution = execution => execution.endpointName === endpointName;
        return this.executions.filter(isExecution);
    },
    add: function(execution) {
        this.executions.push(execution);
        setToLocalstorage('executions', this.executions);

        // create default attempt

    }
};

