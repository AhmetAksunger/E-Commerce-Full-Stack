import axios from "./axiosConfig";

export default class CartItemService {
  /**
   *
   * @param {string} jwt JSON Web Token for authentication.
   * @param {number} cartId Cart Id
   * @param {number} productId Product Id
   */
  addCartItem(jwt, cartId, productId) {
    const data = {
      cartId,
      productId,
    };

    const config = {
      headers: {
        Authorization: `Bearer ${jwt}`,
      },
    };
    return axios.post("/api/v1/cart-items", data, config);
  }
}
