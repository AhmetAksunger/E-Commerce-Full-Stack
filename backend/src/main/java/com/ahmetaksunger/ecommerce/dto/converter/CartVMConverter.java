package com.ahmetaksunger.ecommerce.dto.converter;

import com.ahmetaksunger.ecommerce.dto.response.CartVM;
import com.ahmetaksunger.ecommerce.dto.response.CustomerVM;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.service.CartCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartVMConverter {

    private final MapperService mapperService;
    private final CartItemVMConverter cartItemVMConverter;
    public CartVM convert(Cart from){
        return CartVM.builder()
                .id(from.getId())
                .customer(mapperService.forResponse().map(from.getCustomer(), CustomerVM.class))
                .cartItems(from.getCartItems().stream().map(cartItemVMConverter::convert).toList())
                .totalProductCount(CartCalculator.calculateTotalProductCount(from))
                .total(CartCalculator.calculateTotal(from))
                .build();
    }
}
