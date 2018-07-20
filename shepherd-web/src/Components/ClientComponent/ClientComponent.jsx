import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { endPointsAPI } from '../../mockData';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Grid, Row, Col, FormGroup, ControlLabel, FormControl, Button, ButtonGroup } from 'react-bootstrap';
import '../App.css';
import xml2js from "xml2js";
import { pd } from "pretty-data";
import Chart from "../../service/chart";
import { getVisualizationJSON } from '../../service/service';

function FieldGroup({ id, label, help, ...props }) {
    return (
        <FormGroup controlId={id}>
            <ControlLabel>{label}</ControlLabel>
            <FormControl {...props} />
        </FormGroup>
    );
}

class ClientComponent extends Component {
    constructor(props, context) {
        super(props, context);
        this.handleNameChange = this.handleNameChange.bind(this);
        this.handleXMLChange = this.handleXMLChange.bind(this);
        this.handleJSONChange = this.handleJSONChange.bind(this);
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

    getEndpoints(){
        const clientName = this.props.match.params.clientName;
        endPointsAPI.get(clientName, (data => {
            this.setState({
                endPoints: data,
                isLoading: false,
                nameValue: '',
                XMLValue: '',
                JSONValue: ''
            })
        }));
    }

    handleNameChange(e) {
        this.setState({ nameValue: e.target.value });
    }

    handleXMLChange(e){
        this.setState({ XMLValue: e.target.value });
    }

    showData(endpointName){
        this.state.endPoints.map((function(ep){
            if(ep.endpointName === endpointName){
                this.setState({
                    showData: true,
                    showVisualization: false,
                    currentEndpoint: ep.endpointName,
                    daggraph: pd.xml(ep.daggraph),
                    endpointDetails: ep.endpointDetails
                })
            }
        }).bind(this));
    }

    showGraph(endpointName){
        getVisualizationJSON(this.props.match.params.clientName, endpointName, (function(res){
            this.setState({
                showData: false,
                showVisualization: true,
                graphData: res
            });
        }).bind(this));
    }

    componentDidUpdate(){
        if(this.state.showVisualization)
            Chart().createChart("svg", this.state.graphData);
    }

    deleteEndpoint(endPoint){
        // method stub
    }

    validateXML(e) {
        // sample xml: '<root><content><p xml:space="preserve">This is <b>some</b> content.</p></content></root>'
        try{
            xml2js.parseString(e.target.value, (function(err, result){
                if(!err){
                    this.setState({
                        XMLValue: pd.xml(e.target.value)
                    });
                }
            }).bind(this));
        } catch(err){
            console.log("xml parse fail", err);
        }
    }

    handleJSONChange(e) {
        this.setState({ JSONValue: e.target.value });
    }

    validateJSON(e) {
        try{
            var isValidJSON = JSON.parse(e.target.value);
            console.log(isValidJSON);
        } catch(err){
            console.log("json fail", err);
        }
    }

    showAddEndpoint(){
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

        if(this.state.nameValue.trim() !== '' && this.state.XMLValue.trim() !== '' && this.state.JSONValue.trim() !== '')
            endPointsAPI.add(clientName, this.state.nameValue, this.state.XMLValue, this.state.JSONValue, cb);
    }

    render() {
        if(this.state.isLoading) {
            return <div className="loading-panel"></div>
        }
        const clientName = this.props.match.params.clientName;

        const isEndPointAvailable = this.state.endPoints.length;
        return (
            <Grid fluid={true}>
                <Row className="show-grid">
                    <Col md={3} mdPush={9} className="left-panel">
                        <div>
                            <div className="left-panel-heading">
                                {clientName && <Link className="back" to="/">&laquo;</Link>}
                                <span>{clientName}</span>
                                <span title="Add Endpoint" className="add-element" onClick={this.showAddEndpoint.bind(this)}>+</span>
                            </div>
                            { !isEndPointAvailable && <div className="no-history">No Endpoint Available</div> }
                            { isEndPointAvailable > 0 &&
                                <ul>
                                    {
                                        this.state.endPoints.map(obj => (
                                            <li key={obj.endpointName}>
                                                <span className="end-point">{obj.endpointName}</span>
                                                <ButtonGroup>
                                                    <Button className="xsmall-btn" bsSize="xsmall" onClick={this.showData.bind(this, obj.endpointName)}>Data</Button>
                                                    <Button className="xsmall-btn" bsStyle="info" bsSize="xsmall" onClick={this.showGraph.bind(this, obj.endpointName)}>Visualise Graph</Button>
                                                    <Button className="xsmall-btn execute" bsStyle="success" bsSize="small"><Link to={`/client/${clientName}/${obj.endpointId}`}>Executions</Link></Button>
                                                    <Button className="xsmall-btn delete" bsStyle="danger" bsSize="small" onClick={this.deleteEndpoint.bind(this, obj.endpointId)}>Delete</Button>
                                                </ButtonGroup>
                                            </li>
                                        ))
                                    }
                                </ul>
                            }
                        </div>
                    </Col>
                    <Col md={9} mdPull={9} className="right-panel">
                        {
                            this.state.showData &&
                            <div>
                                <div className="right-panel-heading">Data for `{this.state.currentEndpoint}`</div>
                                <form>
                                    <FormGroup
                                        controlId="formBasicText"
                                    >
                                        <br />
                                        <FieldGroup
                                            id="formControlsText"
                                            type="text"
                                            label="Enter Endpoint name"
                                            placeholder="Endpoint name"
                                            onChange={this.handleNameChange}
                                            value={this.state.currentEndpoint}
                                        />
                                        <FormGroup controlId="formControlsTextarea">
                                            <ControlLabel>Workflow Graph in XML format</ControlLabel>
                                            <FormControl
                                                componentClass="textarea"
                                                placeholder="textarea"
                                                rows="15"
                                                onChange={this.handleXMLChange}
                                                onBlur={this.validateXML.bind(this)}
                                                value={this.state.daggraph}
                                            />
                                        </FormGroup>
                                        <FormGroup controlId="formControlsTextarea">
                                            <ControlLabel>Endpoint Details in JSON format</ControlLabel>
                                            <FormControl
                                                componentClass="textarea"
                                                placeholder="textarea"
                                                rows="25"
                                                onChange={this.handleJSONChange}
                                                onBlur={this.validateJSON}
                                                value={this.state.endpointDetails}
                                            />
                                        </FormGroup>
                                    </FormGroup>
                                </form>
                                <div style={{textAlign: "right"}}>
                                    <Button bsStyle="info" className="update-endpoint" disabled>Update</Button>
                                    <p style={{fontSize: "10px"}}><i>*Update is currently disabled</i></p>
                                </div>
                            </div>
                        }
                        {
                            this.state.showVisualization &&
                            <div>
                                <div className="right-panel-heading">Visualization for `{this.state.currentEndpoint}`</div>
                                <svg width="960" height="600"><g/></svg>
                            </div>
                        }
                        {
                            !(this.state.showData || this.state.showVisualization) &&
                            <div>
                                <div className="right-panel-heading">Add New End Point</div>
                                <form>
                                    <FormGroup
                                        controlId="formBasicText"
                                    >
                                        <br />
                                        <FieldGroup
                                            id="formControlsText"
                                            type="text"
                                            label="Enter Endpoint name"
                                            placeholder="Endpoint name"
                                            onChange={this.handleNameChange}
                                            value={this.state.nameValue}
                                        />
                                        <FormGroup controlId="formControlsTextarea">
                                            <ControlLabel>Workflow Graph in XML format</ControlLabel>
                                            <FormControl
                                                componentClass="textarea"
                                                placeholder="textarea"
                                                onChange={this.handleXMLChange}
                                                onBlur={this.validateXML.bind(this)}
                                                value={this.state.XMLValue}
                                            />
                                        </FormGroup>
                                        <FormGroup controlId="formControlsTextarea">
                                            <ControlLabel>Endpoint Details in JSON format</ControlLabel>
                                            <FormControl
                                                componentClass="textarea"
                                                placeholder="textarea"
                                                onChange={this.handleJSONChange}
                                                onBlur={this.validateJSON}
                                                value={this.state.JSONValue}
                                            />
                                        </FormGroup>
                                        <Button bsStyle="info" className="add-client" onClick={() => this.addEndPoint(clientName)}>
                                            Add Endpoint
                                        </Button>
                                    </FormGroup>
                                </form>
                            </div>
                        }
                    </Col>
                </Row>
            </Grid>
        )
    }
}

export default ClientComponent;