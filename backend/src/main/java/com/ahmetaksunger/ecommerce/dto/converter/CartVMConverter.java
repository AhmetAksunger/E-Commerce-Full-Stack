package com.ahmetaksunger.ecommerce.dto.converter;

import com.ahmetaksunger.ecommerce.dto.response.CartVM;
import com.ahmetaksunger.ecommerce.dto.response.CustomerVM;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.service.CartService;
import com.ahmetaksunger.ecommerce.service.CartCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartVMConverter {

    private final MapperService mapperService;
    private final CartService cartService;
    private final CartItemVMConverter cartItemVMConverter;
    public CartVM convert(Cart from){
        return CartVM.builder()
                .id(from.getId())
                .customer(mapperService.forResponse().map(from.getCustomer(), CustomerVM.class))
                .cartItems(from.getCartItems().stream().map(cartItemVMConverter::convert).collect(Collectors.toList()))
                .totalProductCount(cartService.calculateTotalProductCount(from))
                .total(CartCalculator.calculateTotal(from))
                .build();
    }
}
