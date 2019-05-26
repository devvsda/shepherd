import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { endPointsAPI } from '../../dataController';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Grid, Row, Col, FormGroup, ControlLabel, FormControl, Button } from 'react-bootstrap';
import '../App.css';
import xml2js from 'xml2js';
import { pd } from 'pretty-data';
import Chart from '../../service/chart';
import { getVisualizationJSON } from '../../service/service';
import EndpointListComponent from '../EndpointListComponent';
import UpdateEndpointComponent from '../UpdateEndpointComponent';
import AddEndpointComponent from '../AddEndpointComponent';
import VisualizationGraph from '../VisualizationGraph';

function FieldGroup({ id, label, help, ...props }) {
  return (
    <FormGroup controlId={id}>
      <ControlLabel>{label}</ControlLabel>
      <FormControl {...props} />
    </FormGroup>
  );
}

class ClientComponent extends Component {
  constructor(props) {
    super(props);
    this.handleNameChange = this.handleNameChange.bind(this);
    this.handleXMLChange = this.handleXMLChange.bind(this);
    this.handleJSONChange = this.handleJSONChange.bind(this);
    this.showData = this.showData.bind(this);
    this.showGraph = this.showGraph.bind(this);
    this.deleteEndpoint = this.deleteEndpoint.bind(this);
    this.validateXML = this.validateXML.bind(this);
    this.validateJSON = this.validateJSON.bind(this);
    this.updateEndPoint = this.updateEndPoint.bind(this);
    this.addEndPoint = this.addEndPoint.bind(this);
    this.showAddEndpoint = this.showAddEndpoint.bind(this);

    this.state = {
      nameValue: '',
      XMLValue: '',
      JSONValue: '',
      endPoints: [],
      showData: false,
      showVisualization: false,
      currentEndpoint: '',
      isLoading: true
    };
  }

  componentDidMount() {
    this.getEndpoints();
  }

  getEndpoints() {
    const clientName = this.props.match.params.clientName;
    endPointsAPI.get(clientName, data => {
      this.setState({
        endPoints: data,
        isLoading: false,
        nameValue: '',
        XMLValue: '',
        JSONValue: ''
      });
    });
  }

  endPointUpdated() {
    console.log('updated');
    this.setState({
      isLoading: false
    });
  }

  handleNameChange(e) {
    this.setState({ nameValue: e.target.value });
  }

  handleXMLChange(e) {
    this.setState({ XMLValue: e.target.value });
  }

  showData(endpointName) {
    console.log('called');
    this.state.endPoints.map(
      function(ep) {
        if (ep.endpointName === endpointName) {
          this.setState({
            showData: true,
            showVisualization: false,
            currentEndpoint: ep.endpointName,
            XMLValue: pd.xml(ep.daggraph),
            JSONValue: ep.endpointDetails,
            nameValue: ep.endpointName
          });
        }
      }.bind(this)
    );
  }

  showGraph(endpointName) {
    getVisualizationJSON(
      this.props.match.params.clientName,
      endpointName,
      function(res) {
        this.setState({
          showData: false,
          showVisualization: true,
          graphData: res
        });
      }.bind(this)
    );
  }

  componentDidUpdate() {
    if (this.state.showVisualization) Chart().createChart('svg', this.state.graphData);
  }

  deleteEndpoint(endPoint) {
    // method stub
  }

  validateXML(e) {
    // sample xml: '<root><content><p xml:space="preserve">This is <b>some</b> content.</p></content></root>'
    try {
      xml2js.parseString(
        e.target.value,
        function(err, result) {
          if (!err) {
            this.setState({
              XMLValue: pd.xml(e.target.value)
            });
          }
        }.bind(this)
      );
    } catch (err) {
      console.log('xml parse fail', err);
    }
  }

  handleJSONChange(e) {
    this.setState({ JSONValue: e.target.value });
  }

  validateJSON(e) {
    try {
      var isValidJSON = JSON.parse(e.target.value);
      console.log(isValidJSON);
    } catch (err) {
      console.log('json fail', err);
    }
  }

  showAddEndpoint() {
    console.log('clicked gete');
    this.setState({
      showData: false,
      showVisualization: false,
      currentEndpoint: ''
    });
  }

  addEndPoint(clientName) {
    this.setState({
      isLoading: true
    });
    const cb = this.getEndpoints.bind(this);

    if (this.state.nameValue.trim() !== '' && this.state.XMLValue.trim() !== '' && this.state.JSONValue.trim() !== '')
      endPointsAPI.add(clientName, this.state.nameValue, this.state.XMLValue, this.state.JSONValue, cb);
  }

  updateEndPoint(clientName) {
    this.setState({
      isLoading: true
    });
    const cb = this.endPointUpdated.bind(this);

    if (this.state.nameValue.trim() !== '' && this.state.XMLValue.trim() !== '' && this.state.JSONValue.trim() !== '')
      endPointsAPI.update(clientName, this.state.nameValue, this.state.XMLValue, this.state.JSONValue, cb);
  }

  render() {
    if (this.state.isLoading) {
      return <div className="loading-panel" />;
    }
    const clientName = this.props.match.params.clientName;

    return (
      <Grid fluid={true}>
        <Row className="show-grid">
          <Col md={3} mdPush={9} className="left-panel">
            <div>
              <div className="left-panel-heading">
                {clientName && (
                  <Link className="back" to="/">
                    &laquo;
                  </Link>
                )}
                <span>{clientName}</span>
                <span title="Add Endpoint" className="add-element" onClick={this.showAddEndpoint}>
                  +
                </span>
              </div>
              <EndpointListComponent
                endPoints={this.state.endPoints}
                showGraph={this.showGraph}
                deleteEndpoint={this.deleteEndpoint}
                showData={this.showData}
                clientName={clientName}
              />
            </div>
          </Col>
          <Col md={9} mdPull={9} className="right-panel">
            {this.state.showData && (
              <UpdateEndpointComponent
                currentEndpoint={this.state.currentEndpoint}
                handleNameChange={this.handleNameChange}
                nameValue={this.state.nameValue}
                handleXMLChange={this.handleXMLChange}
                validateXML={this.validateXML}
                XMLValue={this.state.XMLValue}
                handleJSONChange={this.handleJSONChange}
                validateJSON={this.validateJSON}
                JSONValue={this.state.JSONValue}
                updateEndPoint={this.updateEndPoint}
                clientName={clientName}
              />
            )}
            {this.state.showVisualization && (
              <div>
                <div className="right-panel-heading">Visualization for {this.state.currentEndpoint}</div>
                <svg width="960" height="800">
                  <g />
                </svg>
              </div>
            )}
            {!(this.state.showData || this.state.showVisualization) && (
              <AddEndpointComponent
                currentEndpoint={this.state.currentEndpoint}
                handleNameChange={this.handleNameChange}
                nameValue={this.state.nameValue}
                handleXMLChange={this.handleXMLChange}
                validateXML={this.validateXML}
                XMLValue={this.state.XMLValue}
                handleJSONChange={this.handleJSONChange}
                validateJSON={this.validateJSON}
                JSONValue={this.state.JSONValue}
                addEndPoint={this.addEndPoint}
                clientName={clientName}
              />
            )}
          </Col>
        </Row>
      </Grid>
    );
  }
}

export default ClientComponent;
