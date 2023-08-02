package com.ahmetaksunger.ecommerce.exception;

import com.ahmetaksunger.ecommerce.dto.response.ProductVM;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter @Setter
@SuperBuilder
public class InsufficientQuantityResponse extends DefaultExceptionResponse{

    List<ProductVM> productsWithInsufficientStock;
}