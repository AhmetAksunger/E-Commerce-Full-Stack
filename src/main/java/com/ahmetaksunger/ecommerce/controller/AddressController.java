package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.address.CreateAddressRequest;
import com.ahmetaksunger.ecommerce.dto.request.address.UpdateAddressRequest;
import com.ahmetaksunger.ecommerce.dto.response.AddressVM;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.security.CurrentUser;
import com.ahmetaksunger.ecommerce.service.AddressService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class AddressController {

    private final AddressService addressService;

    @PostMapping()
    public ResponseEntity<AddressVM> createAddress(@Validated @RequestBody CreateAddressRequest createAddressRequest,
                                                   @CurrentUser User loggedInUser){
        return ResponseEntity.ok(addressService.create(createAddressRequest,loggedInUser));
    }
    
    @PutMapping("/{addressId}")
    public ResponseEntity<AddressVM> updateAddress(@PathVariable long addressId,
    		@Validated @RequestBody UpdateAddressRequest updateAddressRequest,
    		@CurrentUser User user){
    	return ResponseEntity.ok(addressService.update(addressId,updateAddressRequest,user));
    }
    
    @GetMapping()
    public ResponseEntity<List<AddressVM>> getAddressesByUserId(@RequestParam(name = "userId",required = true) long id , @CurrentUser User loggedInUser){
        return ResponseEntity.ok(addressService.getAddressesByUserId(id,loggedInUser));
    }
}
