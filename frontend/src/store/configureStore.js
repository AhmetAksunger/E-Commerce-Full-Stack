import { legacy_createStore as createStore } from 'redux';
import rootReducer from './rootReducer';
import { devToolsEnhancer } from 'redux-devtools-extension';
import { defaultAuthState } from "./initialValues/authState";
import ls from '../shared/SecureLsConfiguration';


export function configureStore(){

    const authState = ls.get('auth');

    let authStateInLocalStorage = defaultAuthState;

    if(authState){
        authStateInLocalStorage = JSON.parse(authState);
    }

    return createStore(rootReducer,{auth:authStateInLocalStorage},devToolsEnhancer())
}