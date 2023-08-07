import React, { useEffect, useState } from "react";
import { Dropdown, Grid, Menu, Segment } from "semantic-ui-react";
import ProductService from "../services/productService";
import CategoryService from "../services/categoryService";
import { useSelector } from "react-redux";
import ProductCard from "../utils/ProductCard";

const Home = () => {
  const [products, setProducts] = useState({
    totalPages: 0,
    size: 0,
    number: 0,
    content: [],
    last: false,
  });

  const [splittedProductContents, setSplittedProductContents] = useState([
    [],
    [],
    [],
  ]);

  const [categories, setCategories] = useState([
    {
      id: 0,
      name: undefined,
      description: undefined,
    },
  ]);

  const { jwt } = useSelector((state) => state.auth);

  const getProducts = () => {
    let productService = new ProductService();
    productService
      .getProducts(jwt)
      .then((response) => {
        setProducts(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
    splitProducts();
  };

  const splitProducts = () => {
    console.log("in");
    const newSplittedProductContents = [[], [], []];
    let j = 0;
    for (let i = 0; i < products.content.length; i++) {
      if (i !== 0 && i % 3 === 0) {
        j++;
      }
      newSplittedProductContents[j].push(products.content[i]);
    }
    setSplittedProductContents(newSplittedProductContents);
  };

  const getCategories = () => {
    let categoryService = new CategoryService();
    categoryService
      .getCategories(jwt)
      .then((response) => setCategories(response.data))
      .catch((error) => console.log(error));
  };

  useEffect(() => {
    getProducts();
    getCategories();
  }, [products]);

  return (
    <>
      <Menu fluid>
        {categories.map((category, idx) => (
          <Menu.Item>
            <Dropdown text={category.name} simple>
              <Dropdown.Menu>
                <Dropdown.Item
                  text="Description"
                  description={category.description}
                />
              </Dropdown.Menu>
            </Dropdown>
          </Menu.Item>
        ))}
      </Menu>
      <Segment raised>
        <Grid columns={3}>
          <Grid.Row>
            {splittedProductContents[0].map((product, idx) => (
              <Grid.Column>
                <ProductCard product={product} />
              </Grid.Column>
            ))}
          </Grid.Row>
          <Grid.Row>
            {splittedProductContents[1].map((product, idx) => (
              <Grid.Column>
                <ProductCard product={product} />
              </Grid.Column>
            ))}
          </Grid.Row>
          <Grid.Row>
            {splittedProductContents[2].map((product, idx) => (
              <Grid.Column>
                <ProductCard product={product} />
              </Grid.Column>
            ))}
          </Grid.Row>
        </Grid>
      </Segment>
    </>
  );
};

export default Home;
