import React, { useEffect, useState } from "react";
import {
  Button,
  Dropdown,
  Input,
  Label,
  Menu,
} from "semantic-ui-react";
import ProductService from "../services/productService";
import CategoryService from "../services/categoryService";
import { useSelector } from "react-redux";
import { orderOptions, sortOptions } from "../utils/constants";
import ProductList from "../utils/ProductList";

const Home = () => {
  const [products, setProducts] = useState({
    totalPages: 0,
    size: 0,
    number: 0,
    content: [],
    last: false,
  });

  const [categories, setCategories] = useState([
    {
      id: 0,
      name: undefined,
      description: undefined,
    },
  ]);
  const [categoryOptions, setCategoryOptions] = useState([]);

  //Filter & Sorting
  const [selectedOrder, setSelectedOrder] = useState("createdAt");
  const [selectedSort, setSelectedSort] = useState("desc");
  const [selectedMinPrice, setSelectedMinPrice] = useState(0);
  const [selectedMaxPrice, setSelectedMaxPrice] = useState(2147483647);
  const [selectedCategories, setSelectedCategories] = useState([]);
  const [activePage, setActivePage] = useState(0);
  const [filterSortError, setFilterSortError] = useState(false);

  const { jwt } = useSelector((state) => state.auth);

  const getProducts = (
    sort,
    order,
    categoryIds,
    minPrice,
    maxPrice,
    page,
    size
  ) => {
    let productService = new ProductService();
    productService
      .getProducts(
        jwt,
        sort,
        order,
        categoryIds,
        minPrice,
        maxPrice,
        page,
        size
      )
      .then((response) => {
        setProducts(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
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
    if (isNaN(selectedMaxPrice) || isNaN(selectedMinPrice)) {
      setFilterSortError(true);
    } else {
      setFilterSortError(false);
    }
  }, [selectedMinPrice, selectedMaxPrice]);

  useEffect(() => {
    const cOptions = categories.map((category, idx) => ({
      key: category.id,
      text: category.name,
      value: category.id,
    }));

    setCategoryOptions(cOptions);
  }, [categories]);

  const onPageChange = (event, { activePage }) => {
    getProducts(
      selectedSort,
      selectedOrder,
      selectedCategories,
      selectedMinPrice,
      selectedMaxPrice,
      activePage - 1,
      undefined
    );
    setActivePage(activePage - 1);
  };

  const onClickResetFilter = () => {
    setSelectedOrder("createdAt");
    setSelectedSort("desc");
    setSelectedMinPrice(0);
    setSelectedMaxPrice(2147483647);
    setSelectedCategories([]);
    setFilterSortError(false);
    getProducts();
  };

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
                  value={selectedOrder}
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
                  value={selectedSort}
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
                  value={selectedMinPrice}
                />
              </Dropdown.Item>
              <Dropdown.Item>
                Max Price
                <Input
                  placeholder="0"
                  style={{ width: "100px", marginLeft: "0.5rem" }}
                  onClick={(event) => event.stopPropagation()}
                  onChange={(event) => setSelectedMaxPrice(event.target.value)}
                  value={selectedMaxPrice}
                />
              </Dropdown.Item>
              {filterSortError && (
                <div style={{ textAlign: "center" }}>
                  <Label pointing="above" color="red" basic>
                    Enter a numeric value
                  </Label>
                </div>
              )}
              <Dropdown.Item>
                <Dropdown
                  placeholder="Categories"
                  pointing="left"
                  fluid
                  multiple
                  selection
                  options={categoryOptions}
                  value={selectedCategories}
                  onChange={(event, data) => setSelectedCategories(data.value)}
                />
              </Dropdown.Item>
              <Dropdown.Divider />
              <div style={{ textAlign: "center", marginBottom: "0.5rem" }}>
                <Button
                  basic
                  color="green"
                  disabled={filterSortError}
                  onClick={() =>
                    getProducts(
                      selectedSort,
                      selectedOrder,
                      selectedCategories,
                      selectedMinPrice,
                      selectedMaxPrice,
                      activePage
                    )
                  }
                >
                  Apply
                </Button>
                <Button basic color="red" onClick={onClickResetFilter}>
                  Reset
                </Button>
              </div>
            </Dropdown.Menu>
          </Dropdown>
        </Menu.Item>
      </Menu>
      <ProductList products={products} onPageChange={onPageChange} />
    </>
  );
};

export default Home;
