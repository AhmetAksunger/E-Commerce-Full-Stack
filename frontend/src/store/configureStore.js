import { legacy_createStore as createStore } from 'redux';
import rootReducer from './rootReducer';
import { devToolsEnhancer } from 'redux-devtools-extension';
import { defaultAuthState } from "./initialValues/authState";
import ls from '../shared/SecureLsConfiguration';


export function configureStore(){

    let authState = defaultAuthState

    try {
        authState = ls.get('auth');
    } catch (error) {
        console.log(error);
    }

    if(authState){
        authState = JSON.parse(authState);
    }

    return createStore(rootReducer,{auth:authState},devToolsEnhancer())
}