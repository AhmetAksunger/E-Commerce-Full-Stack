package com.ahmetaksunger.ecommerce.service;

import java.util.List;
import java.util.Locale;

import com.ahmetaksunger.ecommerce.exception.NotFoundException.AddressNotFoundException;
import com.ahmetaksunger.ecommerce.service.rules.BaseRules;
import org.springframework.stereotype.Service;

import com.ahmetaksunger.ecommerce.dto.request.address.CreateAddressRequest;
import com.ahmetaksunger.ecommerce.dto.request.address.UpdateAddressRequest;
import com.ahmetaksunger.ecommerce.dto.response.AddressVM;
import com.ahmetaksunger.ecommerce.mapper.MapperService;
import com.ahmetaksunger.ecommerce.model.Address;
import com.ahmetaksunger.ecommerce.model.Country;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.repository.AddressRepository;
import com.ahmetaksunger.ecommerce.service.rules.AddressRules;

import lombok.RequiredArgsConstructor;

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
        address.setUser(user);

        return mapperService.forResponse().map(addressRepository.save(address),AddressVM.class);
    }

	@Override
	public AddressVM update(long addressId, UpdateAddressRequest updateAddressRequest, User user) {
		
		Address address = addressRepository.findById(addressId).orElseThrow(()-> new AddressNotFoundException());

        //Rules
        addressRules.checkIfCanUpdate(address, user);

	   if (updateAddressRequest.getAddress() != null) {
	        address.setAddress(updateAddressRequest.getAddress());
	    }
	    if (updateAddressRequest.getCity() != null) {
	        address.setCity(updateAddressRequest.getCity());
	    }
	    if (updateAddressRequest.getCountry() != null) {
	        address.setCountry(Country.valueOf(updateAddressRequest.getCountry().toUpperCase(Locale.ENGLISH)));
	    }
	    if (updateAddressRequest.getZipCode() != null) {
	        address.setZipCode(updateAddressRequest.getZipCode());
	    }

		return mapperService.forResponse().map(addressRepository.save(address), AddressVM.class);
	}

    @Override
    public void delete(long addressId, User loggedInUser) {
        //Rules
        addressRules.checkIfCanDelete(addressRepository.findById(addressId)
                .orElseThrow(AddressNotFoundException::new),loggedInUser);

        addressRepository.deleteById(addressId);
    }

    @Override
    public List<AddressVM> getAddressesByUserId(long id,User user) {

        //Rules
        BaseRules.checkIfIdsNotMatch(id,user);

        List<Address> addresses = addressRepository.getByUserId(id);
        return addresses.stream()
                .map(address -> mapperService.forResponse().map(address,AddressVM.class))
                .toList();
    }
}
