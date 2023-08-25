package com.ahmetaksunger.ecommerce.dto.converter;

import com.ahmetaksunger.ecommerce.dto.response.CartItemVM;
import com.ahmetaksunger.ecommerce.dto.response.ProductVM;
import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.model.CartItem;
import com.ahmetaksunger.ecommerce.model.Product;
import com.ahmetaksunger.ecommerce.service.PriceCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

class CartItemVMConverterTest {

    private ProductVMConverter productVMConverter;
    private CartItemVMConverter cartItemVMConverter;

    @BeforeEach
    void setUp() {
        productVMConverter = Mockito.mock(ProductVMConverter.class);
        cartItemVMConverter = new CartItemVMConverter(productVMConverter);
        Mockito.mockStatic(PriceCalculator.class);
    }

    @Test
    void whenConvertCalledWithCartItem_itShouldReturnValidCartItemVM() {

        Cart cart = Mockito.mock(Cart.class);
        Product product = Mockito.mock(Product.class);
        ProductVM productVM = Mockito.mock(ProductVM.class);

        CartItem cartItem = CartItem.builder()
                .id(1L)
                .cart(cart)
                .product(product)
                .quantity(100)
                .build();

        Mockito.when(productVMConverter.convert(product)).thenReturn(productVM);
        Mockito.when(PriceCalculator.calculateTotalForCartItem(cartItem)).thenReturn(BigDecimal.TEN);

        CartItemVM result = cartItemVMConverter.convert(cartItem);

        Assertions.assertEquals(result.getId(), cartItem.getId());
        Assertions.assertEquals(result.getProduct(), productVM);
        Assertions.assertEquals(result.getQuantity(), cartItem.getQuantity());
        Assertions.assertEquals(result.getTotal(), BigDecimal.TEN);
    }
}