import axios from "./axiosConfig";

export default class ProductService {

  getProducts(
    jwt,
    sort = "desc",
    order = "createdAt",
    categoryIds,
    minPrice = 0,
    maxPrice = 2147483647,
    page = 0,
    size = 5
  ) {
    const config = {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    };
    return axios.get(`/api/v1/products?sort=${sort}&order=${order}&minPrice=${minPrice}&maxPrice=${maxPrice}&page=${page}&size=${size}`,
     config);
  }
}
