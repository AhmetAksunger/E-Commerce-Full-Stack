package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.order.CreateOrderRequest;
import com.ahmetaksunger.ecommerce.dto.response.OrderCompletedResponse;
import com.ahmetaksunger.ecommerce.dto.response.GetOrdersResponse;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.util.ECommercePagingRequest;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderCompletedResponse create(CreateOrderRequest createOrderRequest, User loggedInUser);

    Page<GetOrdersResponse> getOrdersByCustomerId(Long customerId, User loggedInUser, ECommercePagingRequest paging);
}
