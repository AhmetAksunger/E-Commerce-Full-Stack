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
import UpperMenu from "../layouts/UpperMenu";

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

  return (
    <>
      <UpperMenu />
      <ProductList products={products} onPageChange={onPageChange} />
    </>
  );
};

export default Home;
