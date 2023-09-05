package com.ahmetaksunger.ecommerce.exception;

import com.ahmetaksunger.ecommerce.dto.response.ProductVM;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter @Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class InsufficientQuantityResponse extends DefaultExceptionResponse{

    List<ProductVM> productsWithInsufficientStock;
}