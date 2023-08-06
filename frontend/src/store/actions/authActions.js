export const CUSTOMER_LOGIN_SUCCESS = "C_LOGIN_SUCCESS";

export function customerLoginSuccess(authState){
    return {
        type:CUSTOMER_LOGIN_SUCCESS,
        payload:authState
    }
}