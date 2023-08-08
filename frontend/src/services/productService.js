import axios from "./axiosConfig";

export default class ProductService {

  /**
   * Fetches a list of products based on provided filters and pagination options.
   *
   * @param {string} jwt - JSON Web Token for authentication.
   * @param {string} sort - Sorting order: "asc" or "desc".
   * @param {string} order - Sorting criterion, "createdAt,updatedAt,name,price"
   * @param {number} minPrice - Minimum price for filtering.
   * @param {number} maxPrice - Maximum price for filtering.
   * @param {number} page - Page number.
   * @param {number} size - Number of products per page.
   * @returns {Promise} Promise containing the API response with product list.
   */
  getProducts(
    jwt,
    sort = "desc",
    order = "createdAt",
    minPrice = 0,
    maxPrice = 2147483647,
    page = 0,
    size = 9
  ) {
    const config = {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    };
    return axios.get(
      `/api/v1/products?sort=${sort}&order=${order}&minPrice=${minPrice}&maxPrice=${maxPrice}&page=${page}&size=${size}`,
      config
    );
  }
}
