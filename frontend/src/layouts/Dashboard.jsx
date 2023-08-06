import React from 'react';
import { Route } from 'react-router-dom/cjs/react-router-dom.min';
import CustomerSignUp from '../pages/CustomerSignUp';
import Login from '../pages/Login';

const Dashboard = () => {
    return (
        <div>
            <Route exact path="/sign-up" component={CustomerSignUp}/>
            <Route exact path="/login" component={Login}/>
        </div>
    );
};

export default Dashboard;