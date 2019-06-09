import HomeComponent from '../../Components/HomeComponent/HomeComponent';
import { connect } from 'react-redux';

import { fetchClients } from '../../actions/actions';

const actions = { fetchClients };

function mapStateToProps(state) {
  return {
    clients: state.clients,
    isLoading: state.loading
  };
}

export default connect(
  mapStateToProps,
  actions
)(HomeComponent);
