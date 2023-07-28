package com.ahmetaksunger.ecommerce.dto.response;

import com.ahmetaksunger.ecommerce.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVM {

    private long id;
    private String name;
    private BigDecimal price;
    private int quantity;
    private String logo;
    private SellerVM seller;
    private List<CategoryVM> categories;

}
