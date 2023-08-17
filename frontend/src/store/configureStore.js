import { legacy_createStore as createStore } from 'redux';
import rootReducer from './rootReducer';
import { devToolsEnhancer } from 'redux-devtools-extension';
import { defaultAuthState } from "./initialValues/authState";


export function configureStore(){

    const authState = localStorage.getItem('auth');

    let authStateInLocalStorage = defaultAuthState;

    if(authState){
        authStateInLocalStorage = JSON.parse(authState);
    }

    return createStore(rootReducer,{auth:authStateInLocalStorage},devToolsEnhancer())
}