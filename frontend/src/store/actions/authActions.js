export const LOGIN_SUCCESS = "LOGIN_SUCCESS";
export const LOGOUT_SUCCESS = "LOGOUT_SUCCESS";

export function loginSuccess(authState){
    return {
        type:LOGIN_SUCCESS,
        payload:authState
    }
}

export function logoutSuccess(){
    return {
        type:LOGOUT_SUCCESS
    }
}