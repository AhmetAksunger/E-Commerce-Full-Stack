import { LOGIN_SUCCESS, LOGOUT_SUCCESS } from "../actions/authActions";
import { defaultAuthState } from "../initialValues/authState";


export default function authReducer(state = defaultAuthState, {type,payload}){
    switch (type) {
        case LOGIN_SUCCESS:
            return {
                ...payload,
                isLoggedIn: true
            };
        case LOGOUT_SUCCESS:
            return {
                ...defaultAuthState
            };
        default:
            return state;
    }
}