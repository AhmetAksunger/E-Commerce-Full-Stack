import React, { useEffect, useState } from "react";
import ProductService from "../services/productService";
import CategoryService from "../services/categoryService";
import { useSelector } from "react-redux";
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

  const filters = useSelector((state) => state.filter);

  const getProducts = (filterParams = filters,page,size) => {
    let productService = new ProductService();
    productService.getProducts(jwt,filterParams,page,size)
    .then((response) => setProducts(response.data.response))
    .catch((error) => console.log(error));
  }

  const getCategories = () => {
    let categoryService = new CategoryService();
    categoryService
      .getCategories(jwt)
      .then((response) => setCategories(response.data.response))
      .catch((error) => console.log(error));
  };

  const onPageChange = (activePage) => {
    let activePageIdx = activePage - 1;
    getProducts(filters,activePageIdx);
  }

  useEffect(() => {
    getProducts();
    getCategories();
  }, [filters]);

  return (
    <>
      <UpperMenu categories={categories}/>
      <ProductList products={products} onPageChange={onPageChange} />
    </>
  );
};

export default Home;
