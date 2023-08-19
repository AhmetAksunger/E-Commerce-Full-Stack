import axios from "./axiosConfig";

export default class CartItemService {
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
}
