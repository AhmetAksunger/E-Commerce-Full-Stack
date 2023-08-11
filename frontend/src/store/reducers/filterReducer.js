import { ADD_PRODUCT_FILTERS, RESET_PRODUCT_FILTERS } from "../actions/filterActions";
import { initialFilters } from "../initialValues/filters";

export default function filterReducer(state = initialFilters, {type,payload}){
    switch (type) {
        case ADD_PRODUCT_FILTERS:
            return {
                ...payload
            };
        case RESET_PRODUCT_FILTERS:
            return {
                ...initialFilters
            }
        default:
            return {
                ...state
            }
    }
}