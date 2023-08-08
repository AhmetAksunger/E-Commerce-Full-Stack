import React, { useEffect, useState } from "react";
import {
  Button,
  Dropdown,
  Grid,
  Input,
  Label,
  Menu,
  Pagination,
  Segment,
} from "semantic-ui-react";
import ProductService from "../services/productService";
import CategoryService from "../services/categoryService";
import { useSelector } from "react-redux";
import ProductCard from "../utils/ProductCard";
import { orderOptions, sortOptions } from "../utils/constants";

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

  const [selectedOrder, setSelectedOrder] = useState("createdAt");
  const [selectedSort, setSelectedSort] = useState("desc");
  const [selectedMinPrice, setSelectedMinPrice] = useState(0);
  const [selectedMaxPrice, setSelectedMaxPrice] = useState(2147483647);

  const [filterSortError, setFilterSortError] = useState(false);

  const { jwt } = useSelector((state) => state.auth);

  const getProducts = (sort, order, minPrice, 
    maxPrice, page, size) => {
    let productService = new ProductService();
    productService
      .getProducts(jwt, sort, order, minPrice, maxPrice, page, size)
      .then((response) => {
        setProducts(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const splitProducts = () => {
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
  }, []);

  useEffect(() => {
    splitProducts();
  }, [products]);

  useEffect(() => {
    if (isNaN(selectedMaxPrice) || isNaN(selectedMinPrice)) {
      setFilterSortError(true);
    } else {
      setFilterSortError(false);
    }
  }, [selectedMinPrice, selectedMaxPrice]);

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
        <Menu.Item position="right">
          <Dropdown
            text="Filter & Sort"
            icon="filter"
            floating
            labeled
            button
            className="icon"
          >
            <Dropdown.Menu style={{ width: "200px" }}>
              <Dropdown.Header icon="tags" content="Filter and Sort" />
              <Dropdown.Item>
                <Dropdown
                  placeholder="Order"
                  pointing="left"
                  selection
                  fluid
                  options={orderOptions}
                  onChange={(event, data) => setSelectedOrder(data.value)}
                />
              </Dropdown.Item>
              <Dropdown.Item>
                <Dropdown
                  placeholder="Sort"
                  pointing="left"
                  fluid
                  selection
                  options={sortOptions}
                  onChange={(event, data) => setSelectedSort(data.value)}
                />
              </Dropdown.Item>
              <Dropdown.Item>
                Min Price
                <Input
                  placeholder="0"
                  style={{ width: "100px", marginLeft: "0.5rem" }}
                  onClick={(event) => event.stopPropagation()}
                  onChange={(event) => setSelectedMinPrice(event.target.value)}
                />
              </Dropdown.Item>
              <Dropdown.Item>
                Max Price
                <Input
                  placeholder="0"
                  style={{ width: "100px", marginLeft: "0.5rem" }}
                  onClick={(event) => event.stopPropagation()}
                  onChange={(event) => setSelectedMaxPrice(event.target.value)}
                />
              </Dropdown.Item>
              {filterSortError && (
                <div style={{ textAlign: "center" }}>
                  <Label pointing="above" color="red" basic>
                    Enter a numeric value
                  </Label>
                </div>
              )}
              <Dropdown.Divider />
              <div style={{ textAlign: "center", marginBottom: "0.5rem" }}>
                <Button
                  basic
                  color="green"
                  disabled={filterSortError}
                  onClick={() =>
                    getProducts(selectedSort,selectedOrder,selectedMinPrice,selectedMaxPrice)
                  }
                >
                  Apply
                </Button>
                <Button basic color="red" onClick={() => getProducts()}>
                  Reset
                </Button>
              </div>
            </Dropdown.Menu>
          </Dropdown>
        </Menu.Item>
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
        <Pagination style={{marginTop:"1rem"}}
          boundaryRange={0}
          defaultActivePage={1}
          ellipsisItem={null}
          firstItem={null}
          lastItem={null}
          siblingRange={1}
          totalPages={products.totalPages}
          onPageChange={(event,{ activePage }) => getProducts(undefined,undefined,undefined,undefined,activePage - 1,undefined)}
        />
      </Segment>
    </>
  );
};

export default Home;
