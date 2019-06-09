import React, { Component } from 'react';
import { Switch, Route, Link } from 'react-router-dom';
import {PageHeader} from 'react-bootstrap';
import ClientComponent from './ClientComponent/ClientComponent';
import HomeComponent from './HomeComponent/HomeComponent';
import EndPointComponent from './EndPointComponent/EndPointComponent';

import './App.css';

class App extends Component {
    render() {
        return(
            <main>
                <PageHeader>
                    <Link to="/"><div className="logo">Shepherd</div></Link>
                </PageHeader>
                <div className="wrapper">
                    <Switch>
                        <Route exact path = '/' component={HomeComponent}/>
                        <Route path = '/client/:clientName/:endpointName' component={EndPointComponent}/>
                        <Route path = '/client/:clientName' component={ClientComponent}/>
                    </Switch>
                </div>
                <footer>
                    Shepherd &copy; 2018
                </footer>
            </main>
        )
    }
}


export default App;
