package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.CreateAddressRequest;
import com.ahmetaksunger.ecommerce.dto.response.AddressVM;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.Address;
import com.ahmetaksunger.ecommerce.model.Customer;
import com.ahmetaksunger.ecommerce.model.Seller;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressManager implements AddressService{

    private final AddressRepository addressRepository;
    private final MapperService mapperService;

    @Override
    public AddressVM create(CreateAddressRequest createAddressRequest, User user) {

        Address address = mapperService.forRequest().map(createAddressRequest,Address.class);

        if(user.isCustomer()){
            Customer customer = (Customer) user;
            address.setCustomer(customer);
        }else{
            Seller seller = (Seller) user;
            address.setSeller(seller);
        }

        return mapperService.forResponse().map(addressRepository.save(address),AddressVM.class);
    }

    @Override
    public List<AddressVM> getAddressesByUserId(long id,User user) {
        List<Address> addresses = null;
        List<AddressVM> responses = new ArrayList<>();
        if(user.isCustomer()){
            addresses = addressRepository.getByCustomerId(id);
        }else{
            addresses = addressRepository.getBySellerId(id);
        }

        for (Address address:addresses) {
            AddressVM response = mapperService.forResponse().map(address,AddressVM.class);
            responses.add(response);
        }
        return responses;
    }
}
