import React, { Component } from 'react';

class VisualizationGraph extends Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
      <div>
        <div className="right-panel-heading">Visualization for `{this.props.currentEndpoint}`</div>
        <svg width="960" height="800">
          <g />
        </svg>
      </div>
    );
  }
}

export default VisualizationGraph;
