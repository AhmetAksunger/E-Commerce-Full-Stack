import axios from "./axiosConfig";

export default class ProductService {
  /**
   * Fetches a list of products based on provided filters and pagination options.
   *
   * @param {string} jwt - JSON Web Token for authentication.
   * @param {object} filters - Object that includes all the parameters. {sort,order,search,categoryIds,minPrice,maxPrice}
   * @param {number} page - Page number
   * @param {number} size - Amount of elements on each page
   */
  getProducts(jwt, filters, page=0, size=9) {
    const config = {
      params: {...filters,page,size},
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    };

    return axios.get("/api/v1/products",config);
  }
}
