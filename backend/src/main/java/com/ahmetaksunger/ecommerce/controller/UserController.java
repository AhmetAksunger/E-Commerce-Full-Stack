package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.security.CurrentUser;
import com.ahmetaksunger.ecommerce.service.UserService;
import com.ahmetaksunger.ecommerce.util.ECommerceResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyAuthority('CUSTOMER','SELLER')")
public class UserController {

    private final UserService userService;

    @DeleteMapping("/{id}")
    public ECommerceResponse<Void> deleteUser(@PathVariable long id, @CurrentUser User loggedInUser){
        userService.delete(id,loggedInUser);
        return ECommerceResponse.SUCCESS;
    }
}
