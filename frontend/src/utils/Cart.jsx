import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { Button, Dropdown, Icon, Image } from "semantic-ui-react";
import CartService from "../services/cartService";
import { defaultCart } from "./constants";
import { Link } from "react-router-dom";

const Cart = () => {
  const { jwt, id: userId } = useSelector((state) => state.auth);

  const [cart, setCart] = useState(defaultCart);

  useEffect(() => {
    getCartByUserId();
  }, []);

  const getCartByUserId = () => {
    let cartService = new CartService();
    cartService
      .getCartByUserId(jwt, userId)
      .then((response) => setCart(response.data.response));
  };

  return (
    <Button.Group color="orange">
      <Dropdown
        text="My Cart"
        floating
        labeled
        button
        icon="shopping cart"
        className="icon"
        onClick={() => getCartByUserId()}
      >
        <Dropdown.Menu>
          <Dropdown.Header>{`My Cart(${cart.totalProductCount} Products)`}</Dropdown.Header>
          <Dropdown.Divider />
          {cart.cartItems.map((cartItem, idx) => (
            <Dropdown.Item
              as={Link}
              to={`/products/${cartItem.product.id}`}
              image
            >
              <img src={cartItem.product.logo} />
              <span>{cartItem.product.name}</span>
              <div
                style={{
                  textAlign: "center",
                  marginTop: "1rem",
                  color: "gray",
                }}
              >{`Quantity: ${cartItem.quantity}`}</div>
              <div
                style={{
                  textAlign: "center",
                  marginTop: "1rem",
                  color: "teal",
                }}
              >{`${cartItem.product.price}₺`}</div>
            </Dropdown.Item>
          ))}
          <Dropdown.Divider />
          <Dropdown.Item disabled>
            <strong style={{ color: "black" }}>Total: {cart.total}₺</strong>
          </Dropdown.Item>
          <Dropdown.Item>
            <Button as={Link} to="/my-cart" icon labelPosition="right">
              Go to Cart
              <Icon name="right arrow" />
            </Button>
          </Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
    </Button.Group>
  );
};

export default Cart;
