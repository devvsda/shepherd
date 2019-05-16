import React, { Component } from 'react';
import { executionAPI, attemptsAPI, endPointsAPI } from '../../dataController';
import { Link } from 'react-router-dom';
import { Grid, Row, Col, Button, ButtonGroup, FormGroup, FormControl, InputGroup, Form } from 'react-bootstrap';
import AttemptsComponent from './AttemptsComponent';
import './tree.css';
import Chart from '../../service/chart';
import myTreeData from '../../service/treedata.json';
import { guid } from '../../utils/util';

import delete_first_user_data_attempt1 from '../../service/delete_first_user_data_attempt1.json';
import rms_execution1_attempt1 from '../../service/rms_execution1_attempt1.json';
import delete_user_data_beta_default from '../../service/delete_user_data_beta_default.json';
import delete_user_data_beta_pf1 from '../../service/delete_user_data_beta_pf1.json';
import delete_user_data_beta_ip1 from '../../service/delete_user_data_beta_ip1.json';

function createExecution(endpointName, executionName) {
  let obj = {};
  obj.executionName = executionName;
  obj.endpointName = endpointName;
  obj.executionId = guid();
  return obj;
}

function getCurrentChart(executionId, attemptId, endpointName) {
  console.log('executionId ' + executionId);
  console.log('attemptId ' + attemptId);
  let chartdata = myTreeData; // default data to be shown

  if (endpointName === '46') {
    chartdata = delete_user_data_beta_default;

    if (executionId === 'cf6a-02e8-871d-394a') {
      if (attemptId === 'd085-3d91-57f5-6984') {
        chartdata = delete_user_data_beta_pf1;
      } else if (attemptId === '4bf8-0f3f-2d5b-7ad0') {
        chartdata = delete_user_data_beta_ip1;
      }
    }

    if (executionId === 'e320-a004-2ee5-7bb9') {
      if (attemptId === '4bf8-0f3f-2d5b-7ad0') {
        chartdata = delete_user_data_beta_ip1;
      }
    }
  }

  if (endpointName === '44') {
    chartdata = rms_execution1_attempt1;
  }

  return chartdata;
}

class EndPointComponent extends Component {
  constructor() {
    super();
    this.renderChart = this.renderChart.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.executionAddHandler = this.executionAddHandler.bind(this);
    this.initExecutionAttemptMap = this.initExecutionAttemptMap.bind(this);
    this.updateExecutionAttemptMap = this.updateExecutionAttemptMap.bind(this);

    this.state = {
      currentChart: myTreeData,
      mode: 'execute_workflow',
      payLoad: {},
      isLoading: false,
      executionFieldValue: '',
      executions: [],
      attemptsMap: {},
      currentExecutionInstanceId: ''
    };
  }

  handleChange(e) {
    this.setState({ executionFieldValue: e.target.value });
  }

  setMode(mode) {
    this.setState({
      mode: mode
    });
  }

  renderChart(executionId, attemptId) {
    const endpointName = this.props.match.params.endpointName;
    const currentChart = getCurrentChart(executionId, attemptId, endpointName);

    this.setState({
      mode: 'render_chart',
      currentChart: currentChart,
      currentExecutionInstanceId: executionId
    });
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
        [data.executionId]: [
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
                  <svg className="endpoint" width="960" height="600">
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
