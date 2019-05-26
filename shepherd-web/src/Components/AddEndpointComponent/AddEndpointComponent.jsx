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

class AddEndpointComponent extends Component {
  render() {
    const clientName = this.props.clientName;
    return (
      <div>
        <div className="right-panel-heading">Add New End Point</div>
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
                onChange={this.props.handleJSONChange}
                onBlur={this.props.validateJSON}
                value={this.props.JSONValue}
              />
            </FormGroup>
            <Button bsStyle="info" className="add-client" onClick={() => this.props.addEndPoint(clientName)}>
              Add Endpoint
            </Button>
          </FormGroup>
        </form>
      </div>
    );
  }
}

export default AddEndpointComponent;
