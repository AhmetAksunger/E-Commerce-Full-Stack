package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.address.CreateAddressRequest;
import com.ahmetaksunger.ecommerce.dto.request.address.UpdateAddressRequest;
import com.ahmetaksunger.ecommerce.dto.response.AddressVM;
import com.ahmetaksunger.ecommerce.model.User;

import java.util.List;

public interface AddressService {
    AddressVM create(CreateAddressRequest createAddressRequest, User user);

    List<AddressVM> getAddressesByUserId(long id,User user);

	AddressVM update(long addressId, UpdateAddressRequest updateAddressRequest, User user);
}
