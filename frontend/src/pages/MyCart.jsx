import React, { useEffect, useState } from "react";
import CartService from "../services/cartService";
import { defaultCart } from "../utils/constants";
import { useSelector } from "react-redux";
import {
  Button,
  Card,
  Grid,
  GridColumn,
  Icon,
  Image,
  Input,
  Label,
  Segment,
} from "semantic-ui-react";

const MyCart = () => {
  const { jwt, id: userId, cartId } = useSelector((state) => state.auth);

  const [cart, setCart] = useState(defaultCart);

  useEffect(() => {
    getCartByUserId();
  }, []);

  const getCartByUserId = () => {
    let cartService = new CartService();
    cartService
      .getCartByUserId(jwt, userId)
      .then((response) => setCart(response.data));
  };

  const addCartItem = (productId, quantity) => {
    let cartService = new CartService();
    cartService
      .addCartItem(jwt, cartId, productId, quantity)
      .then((response) => setCart(response.data));
  };

  return (
    <Grid>
      <Grid.Column width={12}>
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
                    <div style={{ display: "flex", alignItems: "center" }}>
                      <Button color="green" size="mini" style={{ margin: "0",height:"37.6px"}} onClick={() => addCartItem(cartItem.product.id,1)}>
                        <Icon name="plus" />
                      </Button>
                      <Input disabled style={{ width: "50px" }} value="1" />
                      <Button color="red" size="mini" style={{ margin: "0",height:"37.6px" }}>
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
