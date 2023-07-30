package com.ahmetaksunger.ecommerce.service.rules;

import com.ahmetaksunger.ecommerce.exception.InvalidRequestParamException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.ProductUpdateNotAllowedException;
import com.ahmetaksunger.ecommerce.exception.NotAllowedException.UnauthorizedException;
import com.ahmetaksunger.ecommerce.exception.NotFoundException.ProductNotFoundException;
import com.ahmetaksunger.ecommerce.model.Product;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductRules {

    private final ProductRepository productRepository;
    private final List<String> validSortParams = List.of("asc","desc");
    private final List<String> validOrderParams = List.of("name","price","createdAt","updatedAt");

    public void checkIfCanUpdate(long productId, User loggedInUser){
        Product product = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException());
        this.verifyProductBelongsToUser(product,loggedInUser, ProductUpdateNotAllowedException.class);
    }

    public void checkIfSortParamIsValid(String sort){
        if(!this.validSortParams.contains(sort.toLowerCase())){
            throw new InvalidRequestParamException("Invalid sort parameter. \nValid sort parameters are: " +
                    this.validSortParams.toString());
        }
    }

    public void checkIfOrderParamIsValid(String order) {
        if(!this.validOrderParams.contains(order)){
            throw new InvalidRequestParamException("Invalid order parameter. " +
                    "\nValid order params are: " + this.validOrderParams.toString());
        }
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
