import initialState from './initialState';
import { FETCH_CLIENTS, RECEIVE_CLIENTS } from '../actions/actionTypes';

export default function loading(state = initialState.isLoading, action) {
  switch (action.type) {
    case FETCH_CLIENTS:
      return true;
    case RECEIVE_CLIENTS:
      return false;
    default:
      return state;
  }
}
