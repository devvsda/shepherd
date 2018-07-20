import React, { Component } from 'react';

class AttemptsComponent extends Component {

    clickHandler(executionId, attemptId) {
        const { renderChart } = this.props;
        renderChart(executionId, attemptId);
    }
    render() {
        const attempts = this.props.attemptObj || [];
        if(attempts.length <= 0) {
            return <div>No attempt has been performed yet</div>
        }

        return (
            <ul>
                {
                    attempts.map((obj, ind) => (
                        <li key={ind} className="attempt" onClick={() => this.clickHandler(obj.executionId, obj.attemptId)}>
                            Attempt {ind+1}
                        </li>
                    ))
                }
            </ul>
        )
    }
}

export default AttemptsComponent;