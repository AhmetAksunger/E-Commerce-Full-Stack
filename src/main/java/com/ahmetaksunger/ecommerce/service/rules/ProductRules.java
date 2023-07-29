package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.NotAllowedException.ProductUpdateNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.ProductNotFoundException;
import com.ahmetaksunger.ecommerce.model.Product;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
@RequiredArgsConstructor
public class ProductRules {

    private final ProductRepository productRepository;
    public void checkIfCanUpdate(long productId, User loggedInUser){
        Product product = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException());
        this.verifyProductBelongsToUser(product,loggedInUser, ProductUpdateNotAllowedException.class);
    }
    private void verifyProductBelongsToUser(Product product, User user,
                                       Class<? extends UnauthorizedException> exceptionClass){
        if(product.getSeller().getId() != user.getId()){
            try {
                throw exceptionClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new UnauthorizedException(e.getMessage());
            }
        }
    }
}
