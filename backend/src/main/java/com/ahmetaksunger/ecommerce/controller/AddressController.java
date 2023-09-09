package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.address.CreateAddressRequest;
import com.ahmetaksunger.ecommerce.dto.request.address.UpdateAddressRequest;
import com.ahmetaksunger.ecommerce.dto.response.AddressVM;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.security.CurrentUser;
import com.ahmetaksunger.ecommerce.service.AddressService;
import com.ahmetaksunger.ecommerce.util.ECommerceResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyAuthority('CUSTOMER','SELLER')")
public class AddressController {

    private final AddressService addressService;

    @PostMapping()
    public ECommerceResponse<AddressVM> createAddress(@Validated @RequestBody CreateAddressRequest createAddressRequest,
                                                      @CurrentUser User loggedInUser){
        return ECommerceResponse.createdOf(addressService.create(createAddressRequest,loggedInUser));
    }
    
    @PutMapping("/{addressId}")
    public ECommerceResponse<AddressVM> updateAddress(@PathVariable long addressId,
                                                   @Validated @RequestBody UpdateAddressRequest updateAddressRequest,
                                                   @CurrentUser User user){
        return ECommerceResponse.successOf(addressService.update(addressId,updateAddressRequest,user));
    }

    @DeleteMapping("/{addressId}")
    public ECommerceResponse<Void> deleteAddress(@PathVariable long addressId, @CurrentUser User loggedInUser){
        addressService.delete(addressId,loggedInUser);
        return ECommerceResponse.SUCCESS;
    }

    @GetMapping("/user/{userId}")
    public ECommerceResponse<List<AddressVM>> getAddressesByUserId(@PathVariable long userId , @CurrentUser User loggedInUser){
        return ECommerceResponse.successOf(addressService.getAddressesByUserId(userId,loggedInUser));
    }
}
