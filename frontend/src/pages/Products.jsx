import React, { useEffect, useState } from 'react';
import { Menu } from 'semantic-ui-react';
import ProductService from '../services/productService';
import { useSelector } from 'react-redux';

const Products = () => {

    const [products,setProducts] = useState();
    const {jwt} = useSelector((state) => state.auth);

    useEffect(()=>{
        let productService = new ProductService();
        productService.getProducts(jwt)
        .then((response) => {
            setProducts(response.data);
        })
        .catch((error) => {
            console.log(error);
        });
    },[]);

    return (
        <Menu>
        </Menu>
    );
};

export default Products;