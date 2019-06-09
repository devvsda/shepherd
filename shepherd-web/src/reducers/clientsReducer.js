import initialState from './initialState';
import { FETCH_CLIENTS, RECEIVE_CLIENTS, ADD_CLIENT } from '../actions/actionTypes';

export default function stuff(state = initialState.clients, action) {
  let newState;
  switch (action.type) {
    case FETCH_CLIENTS:
      console.log('FETCH_CLIENTS Action');
      return action;
    case RECEIVE_CLIENTS:
      debugger;
      newState = action.clients;
      console.log('RECEIVE_CLIENTS Action');
      return newState;
    case ADD_CLIENT:
      console.log('ADD_CLIENT Action');
      return action;
    default:
      return state;
  }
}
