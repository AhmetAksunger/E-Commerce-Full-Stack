import React, { useEffect, useState } from "react";
import CartService from "../services/cartService";
import { defaultCart } from "../utils/constants";
import { useSelector } from "react-redux";
import {
  Button,
  Card,
  Grid,
  GridColumn,
  Header,
  Icon,
  Image,
  Label,
  Segment,
} from "semantic-ui-react";

const MyCart = () => {
  const { jwt, id: userId, cartId } = useSelector((state) => state.auth);

  const [cart, setCart] = useState(defaultCart);

  const [isMaxQuantity, setIsMaxQuantity] = useState([]);
  const [isMinQuantity, setIsMinQuantity] = useState([]);

  useEffect(() => {
    getCartByUserId();
  }, []);

  const getCartByUserId = () => {
    let cartService = new CartService();
    cartService
      .getCartByUserId(jwt, userId)
      .then((response) => setCart(response.data));
  };

  const updateCartItem = (cartItemId, quantity) => {
    let cartService = new CartService();
    cartService
      .updateCartItem(jwt, cartItemId, quantity)
      .then((response) => setCart(response.data));
  };

  const handleIncrementQuantity = (
    cartItemId,
    currentQuantity,
    maxQuantity
  ) => {
    setIsMinQuantity((previousState) =>
      previousState.filter((id) => id !== cartItemId)
    );
    if (currentQuantity === maxQuantity - 1) {
      updateCartItem(cartItemId, currentQuantity + 1);
      setIsMaxQuantity((previousState) => [...previousState, cartItemId]);
    } else {
      updateCartItem(cartItemId, currentQuantity + 1);
    }
  };

  const handleDecrementQuantity = (
    cartItemId,
    currentQuantity,
    minQuantity = 1
  ) => {
    setIsMaxQuantity((previousState) =>
      previousState.filter((id) => id !== cartItemId)
    );
    if (currentQuantity === minQuantity + 1) {
      updateCartItem(cartItemId, currentQuantity - 1);
      setIsMinQuantity((previousState) => [...previousState, cartItemId]);
    } else {
      updateCartItem(cartItemId, currentQuantity - 1);
    }
  };

  return (
    <Grid>
      <Grid.Column width={12}>
        <Header as="h2" textAlign="left">
          <Icon name="shopping cart" />
          <Header.Content>{`My Cart (${cart.totalProductCount} Products)`}</Header.Content>
        </Header>
        <Segment placeholder>
          {cart.cartItems.map((cartItem, idx) => (
            <Card fluid color="orange">
              <Card.Content>
                <div style={{ display: "flex", alignItems: "baseline" }}>
                  <p
                    style={{
                      color: "gray",
                      textAlign: "left",
                      marginRight: "5px",
                    }}
                  >
                    Seller:
                  </p>
                  <p style={{ textAlign: "left" }}>
                    {cartItem.product.seller.companyName}
                  </p>
                </div>
              </Card.Content>
              <Card.Content
                style={{
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                }}
              >
                <Grid centered columns={3} verticalAlign="middle">
                  <Grid.Column width={4}>
                    <div style={{ textAlign: "left" }}>
                      <Image src={cartItem.product.logo} />
                    </div>
                  </Grid.Column>
                  <Grid.Column width={8}>
                    <div style={{ textAlign: "left" }}>
                      <p style={{ fontSize: "1.2rem" }} basic>
                        {cartItem.product.name}
                      </p>
                    </div>
                  </Grid.Column>
                  <GridColumn width={4}>
                    {isMaxQuantity.includes(cartItem.id) && (
                      <div style={{ textAlign: "center" }}>
                        <Label
                          content={`Max. order amount: ${cartItem.product.quantity}`}
                          basic
                          color="red"
                          pointing="below"
                        />
                      </div>
                    )}

                    {isMinQuantity.includes(cartItem.id) && (
                      <div style={{ textAlign: "center" }}>
                        <Label
                          content={`Min. order amount: 1`}
                          basic
                          color="red"
                          pointing="below"
                        />
                      </div>
                    )}

                    <div style={{ display: "flex", alignItems: "center" }}>
                      <Button
                        key={`${cartItem.id}incr`}
                        disabled={isMaxQuantity.includes(cartItem.id)}
                        color="green"
                        size="mini"
                        style={{ margin: "0", height: "31.93px" }}
                        onClick={() =>
                          handleIncrementQuantity(
                            cartItem.id,
                            cartItem.quantity,
                            cartItem.product.quantity
                          )
                        }
                      >
                        <Icon name="plus" />
                      </Button>
                      <Label
                        size="large"
                        basic
                        content={cartItem.quantity}
                        style={{ margin: "0" }}
                      />
                      <Button
                        key={`${cartItem.id}decr`}
                        disabled={isMinQuantity.includes(cartItem.id)}
                        color="red"
                        size="mini"
                        style={{ margin: "0", height: "31.93px" }}
                        onClick={() =>
                          handleDecrementQuantity(
                            cartItem.id,
                            cartItem.quantity
                          )
                        }
                      >
                        <Icon name="minus" />
                      </Button>
                    </div>
                  </GridColumn>
                </Grid>
              </Card.Content>
            </Card>
          ))}
        </Segment>
      </Grid.Column>
      <Grid.Column width={4}>
        <Segment placeholder>4</Segment>
      </Grid.Column>
    </Grid>
  );
};

export default MyCart;
