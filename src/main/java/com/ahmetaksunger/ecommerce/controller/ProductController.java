package com.ahmetaksunger.ecommerce.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@SecurityRequirement(name = "bearerAuth")
@PreAuthorize("hasAnyAuthority('CUSTOMER','SELLER')")
@RequiredArgsConstructor
public class ProductController {

}
