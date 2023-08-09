import React from 'react';
import { Route,Switch } from 'react-router-dom/cjs/react-router-dom.min';
import CustomerSignUp from '../pages/CustomerSignUp';
import Login from '../pages/Login';
import Home from '../pages/Home';
import NotFound from '../pages/NotFound';

const Dashboard = () => {
    return (
        <Switch>
            <Route exact path="/sign-up" component={CustomerSignUp}/>
            <Route exact path="/login" component={Login}/>
            <Route exact path="/" component={Home} />
            <Route path="*" component={NotFound} />
        </Switch>
    );
};

export default Dashboard;