import React, { Component } from 'react';

import { Button, ButtonGroup } from 'react-bootstrap';
import { Link } from 'react-router-dom';

class EndpointListComponent extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const eps = this.props.endPoints;
    const isEndPointAvailable = eps.length;
    const clientName = this.props.clientName;
    return (
      <div>
        {!isEndPointAvailable && <div className="no-history">No Endpoint Available</div>}
        {isEndPointAvailable > 0 && (
          <ul>
            {eps.map(obj => (
              <li key={obj.endpointName}>
                <span className="end-point">{obj.endpointName}</span>
                <ButtonGroup>
                  <Button
                    className="xsmall-btn"
                    bsSize="xsmall"
                    onClick={() => {
                      this.props.showData(obj.endpointName);
                    }}
                  >
                    Data
                  </Button>
                  <Button
                    className="xsmall-btn"
                    bsStyle="info"
                    bsSize="xsmall"
                    onClick={() => {
                      this.props.showGraph(obj.endpointName);
                    }}
                  >
                    Visualise Graph
                  </Button>
                  <Button className="xsmall-btn execute" bsStyle="success" bsSize="small">
                    <Link to={`/client/${clientName}/${obj.endpointName}`}>Executions</Link>
                  </Button>
                  <Button
                    className="xsmall-btn delete"
                    bsStyle="danger"
                    bsSize="small"
                    onClick={() => {
                      this.props.deleteEndpoint(obj.endpointId);
                    }}
                  >
                    Delete
                  </Button>
                </ButtonGroup>
              </li>
            ))}
          </ul>
        )}
      </div>
    );
  }
}

export default EndpointListComponent;
