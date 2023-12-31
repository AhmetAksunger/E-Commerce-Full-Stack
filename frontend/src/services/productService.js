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
      headers: {
        Authorization: `Bearer ${jwt}`,
      }
    };

    const payload = {
      pagination:{
        page,
        size
      },
      filter:{
        search: filters.search,
        categoryIds: filters.categoryIds,
        minPrice: filters.minPrice,
        maxPrice: filters.maxPrice
      },
      sorting:{
        sort:filters.sort,
        direction:filters.direction
      }
    }

    return axios.post("/api/v1/products/get",payload,config);
  }

  /**
   * 
   * Fetches a product by the specified product id.
   * 
   * @param {string} jwt - JSON Web Token for authentication.
   * @param {number} id - Product Id
   * @returns 
   */
  getProductById(jwt, id){
    const config = {
      headers: {
        Authorization: `Bearer ${jwt}`
      }
    };

    return axios.get(`/api/v1/products/${id}`,config);
  }
}
