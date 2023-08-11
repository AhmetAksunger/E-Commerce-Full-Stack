import axios from "./axiosConfig";

export default class ProductService {
  /**
   * Fetches a list of products based on provided filters and pagination options.
   *
   * @param {string} jwt - JSON Web Token for authentication.
   * @param {object} filters - Object that includes all the parameters. {sort,order,search,categoryIds,minPrice,maxPrice,page,size}
   */
  getProducts(jwt, filters) {
    const config = {
      params: filters,
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    };

    return axios.get("/api/v1/products",config);
  }
}
