package com.ahmetaksunger.ecommerce.dto.converter;

import com.ahmetaksunger.ecommerce.dto.response.CategoryVM;
import com.ahmetaksunger.ecommerce.dto.response.ProductOrderInfo;
import com.ahmetaksunger.ecommerce.dto.response.ProductVM;
import com.ahmetaksunger.ecommerce.dto.response.SellerVM;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.Product;
import com.ahmetaksunger.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductVMConverter {

    private final ProductRepository productRepository;
    private final MapperService mapperService;

    public ProductVM convert(Product from) {

        var orderCount = productRepository.getOrderCountByProductId(from.getId());

        return ProductVM.builder()
                .id(from.getId())
                .name(from.getName())
                .description(from.getDescription())
                .price(from.getPrice())
                .quantity(from.getQuantity())
                .orderCount(orderCount == null ? 0 : orderCount)
                .logo(from.getLogo())
                .createdAt(from.getCreatedAt())
                .updatedAt(from.getUpdatedAt())
                .seller(mapperService.forResponse().map(from.getSeller(), SellerVM.class))
                .categories(from.getCategories().stream().map(category -> mapperService.forResponse().map(category, CategoryVM.class)).collect(Collectors.toList()))
                .build();

    }

    public ProductVM convert(ProductOrderInfo from){

        return ProductVM.builder()
                .id(from.getProduct().getId())
                .name(from.getProduct().getName())
                .description(from.getProduct().getDescription())
                .price(from.getProduct().getPrice())
                .quantity(from.getProduct().getQuantity())
                .orderCount(from.getOrderCount())
                .logo(from.getProduct().getLogo())
                .createdAt(from.getProduct().getCreatedAt())
                .updatedAt(from.getProduct().getUpdatedAt())
                .seller(mapperService.forResponse().map(from.getProduct().getSeller(), SellerVM.class))
                .categories(from.getProduct().getCategories().stream().map(category -> mapperService.forResponse().map(category, CategoryVM.class)).collect(Collectors.toList()))
                .build();
    }
}
