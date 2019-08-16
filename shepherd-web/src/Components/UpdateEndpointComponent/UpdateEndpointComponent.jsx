import React, { Component } from 'react';
import { FormGroup, ControlLabel, FormControl, Button } from 'react-bootstrap';

function FieldGroup({ id, label, help, ...props }) {
  return (
    <FormGroup controlId={id}>
      <ControlLabel>{label}</ControlLabel>
      <FormControl {...props} />
    </FormGroup>
  );
}

class UpdateEndpointComponent extends Component {
  constructor(props) {
    super(props);
  }
  render() {
    const clientName = this.props.clientName;
    return (
      <div>
        <div className="right-panel-heading">Endpoint data</div>
        <form>
          <FormGroup controlId="formBasicText">
            <br />
            <FieldGroup
              id="formControlsText"
              type="text"
              label="Enter Endpoint name"
              placeholder="Endpoint name"
              onChange={this.props.handleNameChange}
              value={this.props.nameValue}
            />
            <FormGroup controlId="formControlsTextarea">
              <ControlLabel>Workflow Graph in XML format</ControlLabel>
              <FormControl
                componentClass="textarea"
                placeholder="textarea"
                rows="15"
                onChange={this.props.handleXMLChange}
                onBlur={this.props.validateXML}
                value={this.props.XMLValue}
              />
            </FormGroup>
            <FormGroup controlId="formControlsTextarea">
              <ControlLabel>Endpoint Details in JSON format</ControlLabel>
              <FormControl
                componentClass="textarea"
                placeholder="textarea"
                rows="25"
                onChange={this.props.handleJSONChange}
                onBlur={this.props.validateJSON}
                value={this.props.JSONValue}
              />
            </FormGroup>
          </FormGroup>
        </form>
        <div style={{ textAlign: 'right' }}>
          <Button bsStyle="info" className="update-endpoint" onClick={() => this.props.updateEndPoint(clientName)}>
            Update
          </Button>
          <p style={{ fontSize: '10px' }}>
            <i>*Update is currently disabled</i>
          </p>
        </div>
      </div>
    );
  }
}

export default UpdateEndpointComponent;
