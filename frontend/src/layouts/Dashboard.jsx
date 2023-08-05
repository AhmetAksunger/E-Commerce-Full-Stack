import React from 'react';
import { Route } from 'react-router-dom/cjs/react-router-dom.min';
import CustomerSignUp from '../pages/CustomerSignUp';

const Dashboard = () => {
    return (
        <div>
            <Route exact path="/sign-up" component={CustomerSignUp}/>
        </div>
    );
};

export default Dashboard;