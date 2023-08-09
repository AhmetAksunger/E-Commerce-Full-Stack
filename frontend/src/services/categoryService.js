import axios from "./axiosConfig";

export default class CategoryService{
    
    /**
     * 
     * @param {string} jwt JSON Web Token for authentication.
     * @returns {Promise} Promise containing the API response with categories.
     * 
     */
    getCategories(jwt){
        const config = {
            headers: {
              Authorization: `Bearer ${jwt}`,
            },
          };
        return axios.get("/api/v1/categories",config);
    }
};