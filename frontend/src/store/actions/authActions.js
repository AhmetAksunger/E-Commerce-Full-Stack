export const LOGIN_SUCCESS = "LOGIN_SUCCESS";
export const LOGOUT_SUCCESS = "LOGOUT_SUCCESS";

export function loginSuccess(authState){
    localStorage.setItem('auth',JSON.stringify({...authState, isLoggedIn:true}));
    return {
        type:LOGIN_SUCCESS,
        payload:authState
    }
}

export function logoutSuccess(){
    localStorage.removeItem('auth');
    return {
        type:LOGOUT_SUCCESS
    }
}