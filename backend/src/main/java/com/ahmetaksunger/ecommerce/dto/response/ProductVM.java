package com.ahmetaksunger.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVM {

    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Long orderCount;
    private String logo;
    private Date createdAt;
    private Date updatedAt;
    private SellerVM seller;
    private List<CategoryVM> categories;

}
