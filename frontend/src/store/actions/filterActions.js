export const ADD_PRODUCT_FILTERS = "ADD_FILTERS";
export const RESET_PRODUCT_FILTERS = "RESET_FILTERS";

export function addProductFilters(filters){
    return {
        type:ADD_PRODUCT_FILTERS,
        payload:filters
    };
};

export function resetProductFilters(filters){
    return {
        type: RESET_PRODUCT_FILTERS
    };
};