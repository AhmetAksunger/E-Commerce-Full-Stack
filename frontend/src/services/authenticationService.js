import axios from "axios";

export default class AuthenticationService{
    
    /**
     * Authenticate the user with the provided authentication state.
     * 
     * @param {Object} authState
     * @param {string} authState.email
     * @param {string} authState.password
     * 
     * @returns {Promise}
     */
    authenticate(authState){
        return axios.post(`/api/v1/auth/authenticate`,authState);
    }

    /**
     * Register a new customer with the provided information.
     * @param {Object} customerInfo - The customer information for registration.
     * @param {string} customerInfo.email
     * @param {string} customerInfo.password
     * @param {string} customerInfo.fullName
     * @param {string} customerInfo.phoneNumber
     * 
     * @returns {Promise}
     */
    registerCustomer(customerInfo){
        return axios.post(`/api/v1/auth/register/customer`,customerInfo);
    }
    
    /**
     * Register a new seller with the provided information.
     * @param {Object} sellerInfo - The seller information for registration.
     * @param {string} sellerInfo.email
     * @param {string} sellerInfo.password
     * @param {string} sellerInfo.companyName
     * @param {string} sellerInfo.contactNumber
     * @param {string} sellerInfo.logo
     * 
     * @returns {Promise}
     */
    registerSeller(sellerInfo){
        return axios.post(`/api/v1/auth/register/seller`,sellerInfo);
    }
}