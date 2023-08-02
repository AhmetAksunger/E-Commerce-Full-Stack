package com.ahmetaksunger.ecommerce.exception;

import com.ahmetaksunger.ecommerce.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class InsufficientProductQuantityException extends RuntimeException {

    private final List<Product> productsWithInsufficientStock;
    public InsufficientProductQuantityException(List<Product> productsWithInsufficientStock ){
        super(ExceptionMessages.INSUFF_PRODUCT_QUANTITY.message());
        this.productsWithInsufficientStock = productsWithInsufficientStock;
    }
    public InsufficientProductQuantityException(Product product) {
        super(ExceptionMessages.INSUFF_PRODUCT_QUANTITY.message());
        this.productsWithInsufficientStock = List.of(product);
    }
}
