import * as types from './actionTypes';
import {
  addClient,
  getAllClients,
  fetchEndPoints,
  createEndPoint,
  updateEndPoint,
  executeEndPoint,
  getExecutions
} from '../service/service';

function url() {
  return 'www.url.com';
}

export function receiveClients(data) {
  return { type: types.RECEIVE_CLIENTS, clients: data };
}

// export function fetchClients() {
//   return dispatch => {
//     return fetch(url(), {
//       method: 'GET',
//       mode: 'cors',
//       credentials: 'include',
//       headers: {
//         Accept: 'application/json'
//       }
//     })
//       .then(response => response.json())
//       .then(json => dispatch(receiveClients(json)));
//   };
// }

export function fetchClients() {
  return dispatch => {
    return getAllClients().then(data => dispatch(receiveClients(data)));
  };
}
