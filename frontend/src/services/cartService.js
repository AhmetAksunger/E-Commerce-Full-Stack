import axios from "./axiosConfig";

export default class CartService {
  /**
   *
   * @param {string} jwt JSON Web Token for authentication.
   * @param {number} cartId Cart Id
   * @param {number} productId Product Id
   * @param {number} quantity Quantity
   */
  addCartItem(jwt, cartId, productId,quantity=1) {
    const data = {
      cartId,
      productId,
      quantity
    };

    const config = {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    };
    return axios.post("/api/v1/cart-items", data, config);
  }

  /**
   * 
   * @param {string} jwt JSON Web Token for authentication.
   * @param {number} id User Id
   * @returns 
   */
  getCartByUserId(jwt,id){
    const config = {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    };

    return axios.get(`/api/v1/carts/users/${id}`,config);
  }

}
