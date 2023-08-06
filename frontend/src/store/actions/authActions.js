export const LOGIN_SUCCESS = "LOGIN_SUCCESS";

export function loginSuccess(authState){
    return {
        type:LOGIN_SUCCESS,
        payload:authState
    }
}