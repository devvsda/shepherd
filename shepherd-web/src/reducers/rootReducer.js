import { combineReducers } from 'redux';
import clients from './clientsReducer';
import loading from './loadingReducer';

const rootReducer = combineReducers({
  clients,
  loading
});

export default rootReducer;
