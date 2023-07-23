package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.CreateAddressRequest;
import com.ahmetaksunger.ecommerce.dto.response.AddressVM;
import com.ahmetaksunger.ecommerce.model.User;

import java.util.List;

public interface AddressService {
    AddressVM create(CreateAddressRequest createAddressRequest, User user);

    List<AddressVM> getAddressesByUserId(long id,User user);
}
