import clientsData from './service/clients.json';
import endpoints from './service/endPoints.json';
import executions from './service/executions.json';
import attempt1 from './service/attempt1.json';

import {
  addClient,
  fetchClients,
  fetchEndPoints,
  createEndPoint,
  updateEndPoint,
  executeEndPoint
} from './service/service';

export const clientsAPI = {
  all: function(cb) {
    fetchClients(cb);
  },
  get: function(id) {
    return {};
  },
  add: function(clientName, cb) {
    addClient(clientName, cb);
  }
};

export const endPointsAPI = {
  all: function() {
    return this.endPoints;
  },
  get: function(clientName, cb) {
    fetchEndPoints(clientName, cb);
  },
  add: function(clientName, endpointName, xmlData, jsonData, cb) {
    createEndPoint(clientName, endpointName, xmlData, jsonData, cb);
  },
  update: function(clientName, endpointName, xmlData, jsonData, cb) {
    updateEndPoint(clientName, endpointName, xmlData, jsonData, cb);
  },
  getEndPointById: function(endpointId) {
    return {};
  }
};

export const attemptsAPI = {
  all: function() {
    return {};
  },
  get: function(id) {
    return {};
  },
  add: function(attempt) {}
};

export const executionAPI = {
  all: function() {
    return {};
  },
  get: function(endpointName) {
    return [];
  },
  add: function(clientName, endpointName, initialPayload, cb) {}
};
