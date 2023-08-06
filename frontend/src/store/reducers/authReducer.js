import { CUSTOMER_LOGIN_SUCCESS } from "../actions/authActions";
import { defaultCustomerAuthState } from "../initialValues/authState";

const  initialState = {
    defaultCustomerAuthState: defaultCustomerAuthState
}

export default function authReducer(state = initialState, {type,payload}){
    switch (type) {
        case CUSTOMER_LOGIN_SUCCESS:
            return {
                ...payload
            };
        default:
            return state;
    }
}