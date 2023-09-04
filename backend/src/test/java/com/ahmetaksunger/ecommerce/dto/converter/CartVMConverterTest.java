package com.ahmetaksunger.ecommerce.dto.converter;

import com.ahmetaksunger.ecommerce.dto.response.CartItemVM;
import com.ahmetaksunger.ecommerce.dto.response.CartVM;
import com.ahmetaksunger.ecommerce.dto.response.CustomerVM;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.Cart;
import com.ahmetaksunger.ecommerce.model.CartItem;
import com.ahmetaksunger.ecommerce.model.EntityStatus;
import com.ahmetaksunger.ecommerce.model.Customer;
import com.ahmetaksunger.ecommerce.service.CartCalculator;
import org.junit.jupiter.api.AfterAll;
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
    private CartItemVMConverter cartItemVMConverter;
    private static MockedStatic<CartCalculator> mockedStatic;
    @BeforeEach
    void setUp() {
        mapperService = Mockito.mock(MapperService.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        mockedStatic = Mockito.mockStatic(CartCalculator.class);
        cartItemVMConverter = Mockito.mock(CartItemVMConverter.class);
        cartVMConverter = new CartVMConverter(mapperService,cartItemVMConverter);
    }

    @AfterAll
    static void afterAll() {
        mockedStatic.close();
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
                .status(EntityStatus.ACTIVE)
                .build();

        Mockito.when(mapperService.forResponse()).thenReturn(modelMapper);
        Mockito.when(modelMapper.map(customer, CustomerVM.class)).thenReturn(customerVM);
        Mockito.when(cartItemVMConverter.convert(cartItem)).thenReturn(cartItemVM);
        Mockito.when(CartCalculator.calculateTotalProductCount(cart)).thenReturn(5);
        Mockito.when(CartCalculator.calculateTotal(cart)).thenReturn(BigDecimal.TEN);

        CartVM result = cartVMConverter.convert(cart);

        Assertions.assertEquals(result.getId(), cart.getId());
        Assertions.assertEquals(result.getCustomer(), customerVM);
        Assertions.assertEquals(result.getCartItems(), List.of(cartItemVM));
        Assertions.assertEquals(result.getTotalProductCount(), 5);
        Assertions.assertEquals(result.getTotal(), BigDecimal.TEN);
    }
}