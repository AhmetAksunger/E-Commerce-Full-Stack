package com.ahmetaksunger.ecommerce.dto.converter;

import com.ahmetaksunger.ecommerce.dto.response.GetOrdersResponse;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GetOrdersResponseConverter {

    private final MapperService mapperService;
    private final CartItemVMConverter cartItemVMConverter;

    public GetOrdersResponse convert(Order source) {
        var response = mapperService.forResponse().map(source, GetOrdersResponse.class);
        response
                .setOrderedProducts(source.getCart().getCartItems().stream().map(cartItemVMConverter::convert).toList());
        return response;
    }
}
