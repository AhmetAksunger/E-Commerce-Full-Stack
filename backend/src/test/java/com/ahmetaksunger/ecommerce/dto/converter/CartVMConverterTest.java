package com.ahmetaksunger.ecommerce.dto.converter;

import com.ahmetaksunger.ecommerce.dto.response.CartItemVM;
import com.ahmetaksunger.ecommerce.dto.response.CartVM;
import com.ahmetaksunger.ecommerce.dto.response.CustomerVM;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.model.CartItem;
import com.ahmetaksunger.ecommerce.model.CartStatus;
import com.ahmetaksunger.ecommerce.model.Customer;
import com.ahmetaksunger.ecommerce.service.CartService;
import com.ahmetaksunger.ecommerce.service.PriceCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.List;

class CartVMConverterTest {

    private MapperService mapperService;
    private ModelMapper modelMapper;
    private CartVMConverter cartVMConverter;
    private CartService cartService;
    private CartItemVMConverter cartItemVMConverter;
    @BeforeEach
    void setUp() {
        mapperService = Mockito.mock(MapperService.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        cartService = Mockito.mock(CartService.class);
        Mockito.mockStatic(PriceCalculator.class);
        cartItemVMConverter = Mockito.mock(CartItemVMConverter.class);
        cartVMConverter = new CartVMConverter(mapperService,cartService,cartItemVMConverter);
    }

    @Test
    void whenConvertCalledWithCart_itShouldReturnValidCartVM(){

        Customer customer = Mockito.mock(Customer.class);
        CustomerVM customerVM = Mockito.mock(CustomerVM.class);
        CartItem cartItem = Mockito.mock(CartItem.class);
        CartItemVM cartItemVM = Mockito.mock(CartItemVM.class);

        Cart cart = Cart.builder()
                .id(1L)
                .customer(customer)
                .cartItems(List.of(cartItem))
                .status(CartStatus.ACTIVE)
                .build();

        Mockito.when(mapperService.forResponse()).thenReturn(modelMapper);
        Mockito.when(modelMapper.map(customer, CustomerVM.class)).thenReturn(customerVM);
        Mockito.when(cartItemVMConverter.convert(cartItem)).thenReturn(cartItemVM);
        Mockito.when(cartService.calculateTotalProductCount(cart)).thenReturn(5);
        Mockito.when(PriceCalculator.calculateTotal(cart)).thenReturn(BigDecimal.TEN);

        CartVM result = cartVMConverter.convert(cart);

        Assertions.assertEquals(result.getId(), cart.getId());
        Assertions.assertEquals(result.getCustomer(), customerVM);
        Assertions.assertEquals(result.getCartItems(), List.of(cartItemVM));
        Assertions.assertEquals(result.getTotalProductCount(), 5);
        Assertions.assertEquals(result.getTotal(), BigDecimal.TEN);
    }
}