import { LOGIN_SUCCESS } from "../actions/authActions";
import { defaultAuthState } from "../initialValues/authState";


export default function authReducer(state = defaultAuthState, {type,payload}){
    switch (type) {
        case LOGIN_SUCCESS:
            return {
                ...payload
            };
        default:
            return state;
    }
}