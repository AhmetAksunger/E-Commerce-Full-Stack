import React from 'react';
import { Route } from 'react-router-dom/cjs/react-router-dom.min';
import CustomerSignUp from '../pages/CustomerSignUp';
import Login from '../pages/Login';
import Products from '../pages/Products';

const Dashboard = () => {
    return (
        <div>
            <Route exact path="/sign-up" component={CustomerSignUp}/>
            <Route exact path="/login" component={Login}/>
            <Route exact path="/" component={Products} />
        </div>
    );
};

export default Dashboard;