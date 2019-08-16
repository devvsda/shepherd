import React, { Component } from 'react';
import { executionAPI, attemptsAPI, endPointsAPI } from '../../dataController';
import { Link } from 'react-router-dom';
import { Grid, Row, Col, Button, ButtonGroup, FormGroup, FormControl, InputGroup, Form } from 'react-bootstrap';
import AttemptsComponent from './AttemptsComponent';
import './tree.css';
import Chart from '../../service/chart';
// import myTreeData from '../../service/treedata.json';
import { guid } from '../../utils/util';
import { getVisualizationJSON, fetchExecutionState } from '../../service/service';

import delete_first_user_data_attempt1 from '../../service/delete_first_user_data_attempt1.json';
// import rms_execution1_attempt1 from '../../service/rms_execution1_attempt1.json';
// import delete_user_data_beta_default from '../../service/delete_user_data_beta_default.json';
// import delete_user_data_beta_pf1 from '../../service/delete_user_data_beta_pf1.json';
// import delete_user_data_beta_ip1 from '../../service/delete_user_data_beta_ip1.json';

function createExecution(endpointName, executionName) {
  let obj = {};
  obj.executionName = executionName;
  obj.endpointName = endpointName;
  obj.executionId = guid();
  return obj;
}

class EndPointComponent extends Component {
  constructor() {
    super();
    this.renderChart = this.renderChart.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.executionAddHandler = this.executionAddHandler.bind(this);
    this.initExecutionAttemptMap = this.initExecutionAttemptMap.bind(this);
    this.updateExecutionAttemptMap = this.updateExecutionAttemptMap.bind(this);
    this.getCurrentChart = this.getCurrentChart.bind(this);

    this.state = {
      currentChart: '',
      mode: 'execute_workflow',
      payLoad: {},
      isLoading: false,
      executionFieldValue: '',
      executions: [],
      attemptsMap: {},
      currentExecutionInstanceId: ''
    };
  }

  getCurrentChart(executionId, attemptId, endpointName) {
    console.log('executionId ' + executionId);
    console.log('attemptId ' + attemptId);
  }

  handleChange(e) {
    this.setState({ executionFieldValue: e.target.value });
  }

  setMode(mode) {
    this.setState({
      mode: mode
    });
  }

  renderChart(objectId, executionId) {
    const endpointName = this.props.match.params.endpointName;
    const cn = this.props.match.params.clientName;
    const that = this;
    getVisualizationJSON(
      cn,
      endpointName,
      function(res) {
        fetchExecutionState(cn, endpointName, objectId, executionId, function(response) {
          for (var i = 0; i < response.nodes.length; i++) {
            for (var j = 0; j < res.length; j++) {
              if (response.nodes[i].name == res[j].name) {
                res[j].nodeState = response.nodes[i].nodeState;
                switch (response.nodes[i].nodeState) {
                  case 'PROCESSING':
                    res[j].class = 'type-inprogress';
                    break;
                  case 'COMPLETED':
                    res[j].class = 'type-OK';
                    break;
                  case 'FAILED':
                    res[j].class = 'type-error';
                    break;
                  default:
                    res[j].class = 'type-pending';
                }
                break;
              }
            }
          }
          setTimeout(function() {
            console.log(res);
            that.setState({
              mode: 'render_chart',
              currentChart: res,
              currentExecutionInstanceId: executionId
            });
          }, 1000);
        });
      }.bind(this)
    );
  }

  restartExecution() {
    let attempt = { ...delete_first_user_data_attempt1 };
    attempt.executionId = this.state.currentExecutionInstanceId;
    attempt.attemptId = guid();
    attemptsAPI.add(attempt);

    this.updateExecutionAttemptMap();
  }

  executeEndPoint() {
    const endpointName = this.props.match.params.endpointName;
    const clientName = this.props.match.params.clientName;
    // var execution = createExecution(endpointName, this.state.executionFieldValue);

    executionAPI.add(clientName, endpointName, this.state.executionFieldValue, this.executionAddHandler);
  }

  executionAddHandler(data) {
    console.log('data received');
    console.log(data);
    this.updateExecutionAttemptMap(data);
  }

  updateExecutionAttemptMap(data = {}) {
    const endpointName = this.props.match.params.endpointName;
    let execs = [...this.state.executions];
    let attempts = { ...this.state.attemptsMap };

    const prevAttemptMap = (this.state.attemptsMap && this.state.attemptsMap[data.executionId]) || [];

    if (data.objectId && data.executionId) {
      execs = [...this.state.executions, data.objectId];
      attempts = {
        ...this.state.attemptsMap,
        [data.objectId]: [
          ...prevAttemptMap,
          {
            id: data.executionId,
            wfState: 'PROCESSING'
          }
        ]
      };
    }

    this.setState({
      executions: execs,
      attemptsMap: attempts,
      executionFieldValue: ''
    });
  }

  componentDidMount() {
    this.initExecutionAttemptMap();
  }

  initExecutionAttemptMap() {
    const endpointName = this.props.match.params.endpointName;
    const clientName = this.props.match.params.clientName;
    executionAPI.get(clientName, endpointName, res => {
      const ex = [];
      const at = {};
      res.map(item => {
        if (ex.indexOf(item.objectId) === -1) {
          ex.push(item.objectId);
          if (!at[item.objectId]) {
            at[item.objectId] = [];
          }
          at[item.objectId].push({ id: item.executionId, wfState: item.workflowExecutionState });
        }
      });
      this.setState({
        executions: ex,
        attemptsMap: at,
        executionFieldValue: ''
      });
    });
  }

  componentDidUpdate() {
    if (this.state.mode === 'render_chart') Chart().createChart('svg', this.state.currentChart);
  }

  render() {
    const endpointName = this.props.match.params.endpointName;
    const clientName = this.props.match.params.clientName;

    return (
      <Grid fluid={true}>
        <Row className="show-grid">
          <Col md={3} mdPush={3} className="left-panel">
            <div>
              <div className="left-panel-heading">
                {endpointName && (
                  <Link className="back" to={`/client/${clientName}`}>
                    &laquo;
                  </Link>
                )}
                <span>{endpointName}</span>
                <span title="Add Execution" className="add-element" onClick={() => this.setMode('execute_workflow')}>
                  +
                </span>
              </div>
              <div className="exec-wrapper">
                {this.state.executions.length == 0 && <div className="no-history">No Execution history found</div>}
                {this.state.executions.length > 0 &&
                  this.state.executions.map(obj => (
                    <ul key={obj} className="executions" id={obj}>
                      <li className="execution-name">{obj}</li>
                      <li className="no-hover-effect attempts">
                        <AttemptsComponent
                          renderChart={this.renderChart}
                          attemptObj={this.state.attemptsMap[obj]}
                          executionId={obj}
                        />
                      </li>
                    </ul>
                  ))}
              </div>
            </div>
          </Col>
          <Col md={9} mdPull={9} className="right-panel">
            {this.state.mode === 'execute_workflow' && (
              <div className="add-execution-form-container">
                <FormControl
                  type="text"
                  value={this.state.value}
                  placeholder="Initial payload"
                  onChange={this.handleChange}
                />
                <Button bsStyle="primary" className="add-client" onClick={() => this.executeEndPoint()}>
                  Execute endpoint
                </Button>
              </div>
            )}
            {this.state.executions.length > 0 && this.state.mode === 'render_chart' && (
              <div>
                <ButtonGroup vertical={true} className="execution-actions">
                  <Button bsStyle="success" bsSize="small">
                    Resume
                  </Button>
                  <Button
                    bsStyle="primary"
                    bsSize="small"
                    onClick={() => {
                      this.restartExecution();
                    }}
                  >
                    Restart
                  </Button>
                  <Button bsStyle="danger" bsSize="small">
                    Kill
                  </Button>
                </ButtonGroup>
                <div>
                  <svg className="endpoint" width="960" height="800">
                    <g />
                  </svg>
                </div>
              </div>
            )}
          </Col>
        </Row>
      </Grid>
    );
  }
}

export default EndPointComponent;
