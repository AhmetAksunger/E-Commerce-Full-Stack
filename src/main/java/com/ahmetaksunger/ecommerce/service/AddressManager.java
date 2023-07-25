package com.ahmetaksunger.ecommerce.service;

import com.ahmetaksunger.ecommerce.dto.request.CreateAddressRequest;
import com.ahmetaksunger.ecommerce.dto.response.AddressVM;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.*;
import com.ahmetaksunger.ecommerce.repository.AddressRepository;
import com.ahmetaksunger.ecommerce.service.rules.AddressRules;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AddressManager implements AddressService{

    private final AddressRepository addressRepository;
    private final MapperService mapperService;
    private final AddressRules addressRules;

    @Override
    public AddressVM create(CreateAddressRequest createAddressRequest, User user) {

        Address address = mapperService.forRequest().map(createAddressRequest,Address.class);
        address.setCountry(Country.valueOf(createAddressRequest.getCountry().toUpperCase(Locale.ENGLISH)));
        address.setCreatedAt(new Date());
        
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

        //Rules
        addressRules.checkAuthorization(id,user);

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
