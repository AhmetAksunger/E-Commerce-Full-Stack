import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom/cjs/react-router-dom.min";
import ProductService from "../services/productService";
import { useSelector } from "react-redux";
import {
  Button,
  Card,
  Divider,
  Grid,
  Header,
  Icon,
  Image,
  Label,
  Segment,
  Statistic,
  Tab,
  Table,
} from "semantic-ui-react";
import { defaultProduct } from "../utils/constants";
import CartItemService from "../services/cartItemService";
import { ToastContainer, toast } from "react-toastify";

const ProductDetail = () => {
  const { id: productId } = useParams();
  const { jwt, cartId } = useSelector((state) => state.auth);

  const [product, setProduct] = useState(defaultProduct);

  useEffect(() => {
    getProductById();
  }, []);

  const getProductById = () => {
    let productService = new ProductService();
    productService
      .getProductById(jwt, productId)
      .then((response) => setProduct(response.data));
  };

  const onClickAddToCart = () => {
    let cartItemService = new CartItemService();
    cartItemService
      .addCartItem(jwt, cartId, productId, 1)
      .then((response) => null);
    notify();
  };

  const notify = () => {
    return toast.success("Added to the cart", {
      position: "top-right",
      autoClose: 2000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      theme: "light",
    });
  };

  return (
    <Segment>
      <ToastContainer
        position="top-right"
        autoClose={2000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
        theme="light"
      />
      <Grid>
        <Grid.Column width={6}>
          <Segment placeholder style={{ height: "100%" }}>
            <Image src={product.logo} fluid />
          </Segment>
        </Grid.Column>
        <Grid.Column width={10}>
          <Segment placeholder>
            <div>
              <Label basic size="massive">
                {product.name}
              </Label>
            </div>
            <div
              style={{
                textAlign: "left",
                marginTop: "1rem",
                marginBottom: "1rem",
              }}
            >
              <Label as="a" color="teal" size="medium" image>
                <Image src={product.seller.logo} />
                {product.seller.companyName}
              </Label>
            </div>
            <div style={{ textAlign: "left" }}>
              <Statistic size="small">
                <Statistic.Value>{`${product.price}₺`}</Statistic.Value>
              </Statistic>
            </div>
            <Divider />
            <div>
              <Button
                size="massive"
                color="orange"
                animated
                style={{ width: "100%" }}
                onClick={onClickAddToCart}
              >
                <Button.Content visible>Add to Cart</Button.Content>
                <Button.Content hidden>
                  <Icon name="shopping cart" />
                </Button.Content>
              </Button>
            </div>
            <Divider horizontal>
              <Header as="h4">
                <Icon name="tag" />
                Description
              </Header>
            </Divider>
            <p>
              Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent
              eleifend laoreet tellus, ac varius lorem mollis sit amet. Morbi
              velit ante, commodo tempor nibh et, dignissim faucibus nisi. Morbi
              tempus tellus ligula, et lobortis ante dictum vitae. Vestibulum
              felis ante, elementum vitae sollicitudin eget, posuere eu orci.
              Fusce id commodo arcu. Sed sit amet vehicula sem. In molestie erat
              nisi, ac auctor massa fringilla sit amet. Vestibulum pulvinar
              metus condimentum purus ullamcorper, eu euismod magna aliquam.
              Proin pretium condimentum gravida. Morbi sed ante dolor. Donec nec
              accumsan orci, eu volutpat magna.
            </p>
            <Divider horizontal>
              <Header as="h4">
                <Icon name="chart bar" />
                Statistics & Categories
              </Header>
            </Divider>
            <Grid centered>
              <Grid.Row>
                <Statistic.Group size="tiny">
                  <Statistic>
                    <Statistic.Value>
                      <Icon name="box" />
                      {product.quantity}
                    </Statistic.Value>
                    <Statistic.Label>In Stock</Statistic.Label>
                  </Statistic>
                  <Statistic>
                    <Statistic.Value>
                      <Icon name="shipping" />
                      1023
                    </Statistic.Value>
                    <Statistic.Label>Total Orders</Statistic.Label>
                  </Statistic>
                </Statistic.Group>
              </Grid.Row>
              <Grid.Row>
                <strong>Categories</strong>
                <Table>
                  {product.categories.map((category, idx) => (
                    <Table.Row>
                      <Table.Cell>{category.name}</Table.Cell>
                    </Table.Row>
                  ))}
                </Table>
              </Grid.Row>
            </Grid>
          </Segment>
        </Grid.Column>
      </Grid>
    </Segment>
  );
};

export default ProductDetail;
