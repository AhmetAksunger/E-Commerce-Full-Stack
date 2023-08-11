import { combineReducers } from "redux";
import authReducer from "./reducers/authReducer";
import filterReducer from "./reducers/filterReducer";

const rootReducer = combineReducers({
    auth : authReducer,
    filter : filterReducer
});

export default rootReducer;