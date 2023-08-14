package com.ahmetaksunger.ecommerce.controller;

import com.ahmetaksunger.ecommerce.dto.request.withdraw.WithdrawRevenueRequest;
import com.ahmetaksunger.ecommerce.dto.response.WithdrawSuccessResponse;
import com.ahmetaksunger.ecommerce.model.User;
import com.ahmetaksunger.ecommerce.security.CurrentUser;
import com.ahmetaksunger.ecommerce.service.SellerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sellers")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('SELLER')")
public class SellerController {
    private final SellerService sellerService;

    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawSuccessResponse> withdraw(@RequestBody @Valid WithdrawRevenueRequest withdrawRevenueRequest,
                                                            @CurrentUser User loggedInUser) {
        return ResponseEntity.ok(sellerService.withdraw(withdrawRevenueRequest, loggedInUser));
    }
}
