import axios from "./axiosConfig";

export default class ProductService {
  /**
   * Fetches a list of products based on provided filters and pagination options.
   *
   * @param {string} jwt - JSON Web Token for authentication.
   * @param {object} params - Object that includes all the parameters. {sort,order,search,categoryIds,minPrice,maxPrice,page,size}
   */
  getProducts(jwt, params) {
    const config = {
      params: params,
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    };

    return axios.get("/api/v1/products",config);
  }
}
