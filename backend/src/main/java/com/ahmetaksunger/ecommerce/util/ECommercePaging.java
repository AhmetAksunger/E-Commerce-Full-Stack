package com.ahmetaksunger.ecommerce.util;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class ECommercePaging {

    @NotNull
    @Range(min = 0)
    private Integer page;

    @NotNull
    @Range(max = 10)
    private Integer size;

    /**
     * Converts the current instance of ECommercePaging to a Pageable object without sorting.
     *
     * @return A Pageable object with the specified page and size.
     */
    public Pageable toPageable(){
        return PageRequest.of(this.page,this.size);
    }

    /**
     * Converts the current instance of ECommercePaging to a Pageable object with the specified sorting criteria.
     *
     * @param sort The Sort object defining the sorting criteria.
     * @return A Pageable object with the specified page, size, and sorting criteria.
     */
    public Pageable toPageable(final Sort sort){
        return PageRequest.of(this.page,this.size,sort);
    }
}
