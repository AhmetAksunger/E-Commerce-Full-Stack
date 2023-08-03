package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.address.CreateAddressRequest;
import com.ahmetaksunger.ecommerce.dto.request.address.UpdateAddressRequest;
import com.ahmetaksunger.ecommerce.dto.response.AddressVM;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.security.CurrentUser;
import com.ahmetaksunger.ecommerce.service.AddressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AddressVM> createAddress(@Validated @RequestBody CreateAddressRequest createAddressRequest,
                                                   @CurrentUser User loggedInUser){
        return new ResponseEntity<>(addressService.create(createAddressRequest,loggedInUser), HttpStatus.CREATED);
    }
    
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressVM> updateAddress(@PathVariable long addressId,
                                                   @Validated @RequestBody UpdateAddressRequest updateAddressRequest,
                                                   @CurrentUser User user){
        return ResponseEntity.ok(addressService.update(addressId,updateAddressRequest,user));
    }

    @DeleteMapping("/{addressId}")
    public void deleteAddress(@PathVariable long addressId, @CurrentUser User loggedInUser){
        addressService.delete(addressId,loggedInUser);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressVM>> getAddressesByUserId(@PathVariable long userId , @CurrentUser User loggedInUser){
        return ResponseEntity.ok(addressService.getAddressesByUserId(userId,loggedInUser));
    }
}
