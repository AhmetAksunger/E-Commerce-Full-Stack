package com.ahmetaksunger.ecommerce.exception;

import com.ahmetaksunger.ecommerce.model.Product;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.util.List;

@Getter @Setter
public class InsufficientProductQuantityException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7245645242394266512L;
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
