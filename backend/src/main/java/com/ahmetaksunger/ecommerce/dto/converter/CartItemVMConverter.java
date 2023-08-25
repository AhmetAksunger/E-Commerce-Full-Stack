package com.ahmetaksunger.ecommerce.dto.converter;

import com.ahmetaksunger.ecommerce.dto.response.CartItemVM;
import com.ahmetaksunger.ecommerce.model.CartItem;
import com.ahmetaksunger.ecommerce.service.PriceCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartItemVMConverter {

    private final ProductVMConverter productVMConverter;

    public CartItemVM convert(CartItem from){
        return CartItemVM.builder()
                .id(from.getId())
                .product(productVMConverter.convert(from.getProduct()))
                .quantity(from.getQuantity())
                .total(PriceCalculator.calculateTotalForCartItem(from))
                .build();
    }
}
