package com.ahmetaksunger.ecommerce.dto.converter;

import com.ahmetaksunger.ecommerce.dto.response.CategoryVM;
import com.ahmetaksunger.ecommerce.dto.response.ProductVM;
import com.ahmetaksunger.ecommerce.dto.response.SellerVM;
import com.ahmetaksunger.ecommerce.mapper.MapperManager;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.Category;
import com.ahmetaksunger.ecommerce.model.Product;
import com.ahmetaksunger.ecommerce.model.Seller;
import com.ahmetaksunger.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductVMConverterTest {

    private ProductRepository productRepository;
    private MapperService mapperService;
    private ProductVMConverter productVMConverter;
    private ModelMapper modelMapper;
    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        mapperService = Mockito.mock(MapperService.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        productVMConverter = new ProductVMConverter(productRepository,mapperService);
    }

    @Test
    void whenConvertCalledWithProduct_itShouldReturnValidProductVM(){

        Seller seller = Mockito.mock(Seller.class);
        SellerVM sellerVM = Mockito.mock(SellerVM.class);
        Category category = Mockito.mock(Category.class);
        CategoryVM categoryVM = Mockito.mock(CategoryVM.class);

        Product product = Product.builder()
                .id(1L)
                .name("product")
                .description("description")
                .price(BigDecimal.TEN)
                .quantity(5)
                .logo("logo")
                .seller(seller)
                .categories(List.of(category))
                .build();

        Mockito.when(productRepository.getOrderCountByProductId(product.getId())).thenReturn(100L);
        Mockito.when(mapperService.forResponse()).thenReturn(modelMapper);
        Mockito.when(modelMapper.map(seller, SellerVM.class)).thenReturn(sellerVM);
        Mockito.when(modelMapper.map(category,CategoryVM.class)).thenReturn(categoryVM);

        ProductVM result = productVMConverter.convert(product);

        Assertions.assertEquals(result.getId(),product.getId());
        Assertions.assertEquals(result.getName(), product.getName());
        Assertions.assertEquals(result.getDescription(), product.getDescription());
        Assertions.assertEquals(result.getPrice(), product.getPrice());
        Assertions.assertEquals(result.getQuantity(), product.getQuantity());
        Assertions.assertEquals(result.getLogo(), product.getLogo());
        Assertions.assertEquals(result.getSeller(), sellerVM);
        Assertions.assertEquals(result.getCategories(), List.of(categoryVM));
        Assertions.assertEquals(result.getOrderCount(), 100L);
    }
}