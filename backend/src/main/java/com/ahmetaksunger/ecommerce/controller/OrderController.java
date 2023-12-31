package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.order.CreateOrderRequest;
import com.ahmetaksunger.ecommerce.dto.response.GetOrdersResponse;
import com.ahmetaksunger.ecommerce.dto.response.OrderCompletedResponse;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.security.CurrentUser;
import com.ahmetaksunger.ecommerce.service.OrderService;
import com.ahmetaksunger.ecommerce.util.ECommercePagingRequest;
import com.ahmetaksunger.ecommerce.util.ECommerceResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('CUSTOMER')")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ECommerceResponse<OrderCompletedResponse> createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest,
                                                                 @CurrentUser User loggedInUser){
        return ECommerceResponse.createdOf(orderService.create(createOrderRequest,loggedInUser));
    }

    @PostMapping("/customer/{customerId}")
    public ECommerceResponse<Page<GetOrdersResponse>> getOrdersByCustomerId(@PathVariable Long customerId,
                                                                         @CurrentUser User loggedInUser,
                                                                         @RequestBody @Valid ECommercePagingRequest paging){
        return ECommerceResponse.successOf(orderService.getOrdersByCustomerId(customerId,loggedInUser,paging));
    }
}
