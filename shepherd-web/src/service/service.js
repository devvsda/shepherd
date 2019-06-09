// IMPORT DATA FROM STATIC JSON FILE

// COMPONENT
const registerClientUrl = 'http://3.95.163.243:8080/shephard-core/register/client';
const fetchClientsUrl = 'http://3.95.163.243:8080/shephard-core/retrieve/clients';
const fetchEndpointsUrl = 'http://3.95.163.243:8080/shephard-core/retrieve/endpoints';
const createEndpointUrl = 'http://3.95.163.243:8080/shephard-core/register/endpoint';
const getGraphJSON = 'http://3.95.163.243:8080/shephard-core/retrieve/graphXML';
const updateEndPointUrl = 'http://3.95.163.243:8080/shephard-core/update/endpoint/endpointDetails';
const executeEndPointUrl = 'http://3.95.163.243:8080/shephard-core/execute/endpoint';
const getExecutionStateUrl = 'http://3.95.163.243:8080/shephard-core/retrieve/executionState';
const getExecutionsUrl = 'http://3.95.163.243:8080/shephard-core/retrieve/getAllExecutions';

/**
 * get all client
 * @returns {Promise<any>}
 */
export const fetchClients = cb => {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState === 4 && xhttp.status === 200) {
      const res = JSON.parse(xhttp.responseText);
      if (typeof cb === 'function') cb(res.response_data.registered_clients);
    }
  };

  xhttp.open('GET', fetchClientsUrl, true);
  xhttp.setRequestHeader('Content-type', 'application/json');
  xhttp.send();
};

/**
 * add new client
 * @param clientName
 * @returns {Promise<any>}
 */
export const addClient = (clientName, cb) => {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState === 4 && xhttp.status === 200) {
      const res = {
        clientName: clientName,
        clientId: JSON.parse(xhttp.responseText).clientId
      };
      if (typeof cb === 'function') cb(res);
    }
  };
  xhttp.open('POST', registerClientUrl, true);
  xhttp.setRequestHeader('Content-type', 'application/json');
  xhttp.send(JSON.stringify({ clientName: clientName }));
};

/**
 * get all the endpoint for a client
 * @param clientId
 * @returns {Promise<any>}
 */
export const fetchEndPoints = (clientName, cb) => {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState === 4 && xhttp.status === 200) {
      const res = JSON.parse(xhttp.responseText);
      if (typeof cb === 'function') cb(res.response_data.registered_endpoints);
    }
  };
  const url = fetchEndpointsUrl + '?clientName=' + clientName;
  xhttp.open('GET', url, true);
  xhttp.setRequestHeader('Content-type', 'application/json');
  xhttp.send();
};

/**
 * create endpoint
 * @param graphDetails
 * @param nodesDetails
 * @returns {Promise<any>}
 */
export const createEndPoint = (clientName, endpointName, graphDetails, nodesDetails, cb) => {
  var xhttp = new XMLHttpRequest();
  const req = {
    clientName: clientName,
    endpointName: endpointName,
    graphDetails: graphDetails,
    nodesDetails: nodesDetails
  };
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState === 4 && xhttp.status === 200) {
      const res = {
        endpointName: endpointName,
        clientName: clientName
      };
      if (typeof cb === 'function') cb(res);
    }
  };
  xhttp.open('POST', createEndpointUrl, true);
  xhttp.setRequestHeader('Content-type', 'application/json');
  xhttp.send(JSON.stringify(req));
};

export const updateEndPoint = (clientName, endpointName, graphDetails, nodesDetails, cb) => {
  var xhttp = new XMLHttpRequest();
  const req = {
    clientName: clientName,
    endpointName: endpointName,
    graphDetails: graphDetails,
    nodesDetails: nodesDetails
  };
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState === 4 && xhttp.status === 200) {
      const res = {
        endpointName: endpointName,
        clientName: clientName
      };
      if (typeof cb === 'function') cb(res);
    }
  };
  xhttp.open('POST', updateEndPointUrl, true);
  xhttp.setRequestHeader('Content-type', 'application/json');
  xhttp.send(JSON.stringify(req));
};

/**
 * fetch all the execution for the endpointId
 * @param endpointId
 * @returns {Promise<any>}
 */
export const fetchExecutionState = (clientName, endpointName, objectId, executionId, cb) => {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState === 4 && xhttp.status === 200) {
      const res = JSON.parse(xhttp.responseText);
      if (typeof cb === 'function') cb(res.response_data.executionDetails);
    }
  };

  const url =
    getExecutionStateUrl +
    '?clientName=' +
    clientName +
    '&endpointName=' +
    endpointName +
    '&objectId=' +
    objectId +
    '&executionId=' +
    executionId;
  xhttp.open('GET', url, true);
  xhttp.setRequestHeader('Content-type', 'application/json');
  xhttp.send();
};

/**
 * execute workflow
 * @param client_name
 * @param endponit_name
 * @param payload
 * @returns {Promise<any>}
 */
export const executeEndPoint = (clientName, endpointName, initialPayload, cb) => {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState === 4 && xhttp.status === 200) {
      const res = JSON.parse(xhttp.responseText);
      if (typeof cb === 'function') cb(res.response_data);
    }
  };

  const url = executeEndPointUrl;
  xhttp.open('POST', url, true);
  xhttp.setRequestHeader('Content-type', 'application/json');

  const reqStr = JSON.stringify({
    clientName,
    endpointName,
    initialPayload
  });

  xhttp.send(reqStr);
};

export const getVisualizationJSON = (clientName, endpointName, cb) => {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState === 4 && xhttp.status === 200) {
      const res = JSON.parse(xhttp.responseText);
      if (typeof cb === 'function') cb(res.response_data.graph);
    }
  };

  const url = getGraphJSON + '?clientName=' + clientName + '&endpointName=' + endpointName;
  xhttp.open('GET', url, true);
  xhttp.setRequestHeader('Content-type', 'application/json');
  xhttp.send();
};

export const getExecutions = (clientName, endpointName, cb) => {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (xhttp.readyState === 4 && xhttp.status === 200) {
      const res = JSON.parse(xhttp.responseText);
      if (typeof cb === 'function') cb(res.response_data.executionDetails);
    }
  };

  const url = getExecutionsUrl + '?clientName=' + clientName + '&endpointName=' + endpointName;
  xhttp.open('GET', url, true);
  xhttp.setRequestHeader('Content-type', 'application/json');
  xhttp.send();
};
