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
  Table,
} from "semantic-ui-react";

const ProductDetail = () => {
  const { id } = useParams();
  const { jwt } = useSelector((state) => state.auth);

  const [product, setProduct] = useState({});

  useEffect(() => {
    getProductById();
  }, []);

  const getProductById = () => {
    let productService = new ProductService();
    const response = productService
      .getProductById(jwt, id)
      .then((response) => setProduct(response.data))
      .catch((error) => console.log(error));
  };

  return (
    <Segment>
      <Grid>
        <Grid.Column width={6}>
          <Segment placeholder style={{ height: "100%" }}>
            <Image
              src="https://pigment.github.io/fake-logos/logos/medium/color/2.png"
              fluid
            />
          </Segment>
        </Grid.Column>
        <Grid.Column width={10}>
          <Segment placeholder>
            <div>
              <Label basic size="massive">
                Product Name Here
              </Label>
            </div>
            <div style={{ textAlign: "left", marginTop:"1rem",marginBottom:"1rem"}}>
              <Label as="a" color="teal" size="medium" image>
                <Image src="https://pigment.github.io/fake-logos/logos/medium/color/2.png"/>
                Company Name Here
              </Label>{" "}
            </div>
            <div style={{ textAlign: "left" }}>
              <Statistic size="small">
                <Statistic.Value>123₺</Statistic.Value>
              </Statistic>
            </div>
            <Divider />
            <div>
              <Button
                size="massive"
                color="orange"
                animated
                style={{ width: "100%" }}
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
                      100
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
                  <Table.Row>
                    <Table.Cell>a</Table.Cell>
                  </Table.Row>
                  <Table.Row>
                    <Table.Cell>a</Table.Cell>
                  </Table.Row>
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
