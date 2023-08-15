package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.order.CreateOrderRequest;
import com.ahmetaksunger.ecommerce.dto.response.OrderCompletedResponse;
import com.ahmetaksunger.ecommerce.model.User;

public interface OrderService {
    OrderCompletedResponse create(CreateOrderRequest createOrderRequest, User loggedInUser);
}
