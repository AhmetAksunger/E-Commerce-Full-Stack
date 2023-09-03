package com.ahmetaksunger.ecommerce.util;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class ECommerceSortingRequest {

    private Sorting sorting;
    @Getter
    @Setter
    public static class Sorting{
        private String sort;

        private Sort.Direction direction;
    }


    /**
     * Converts the current instance of ECommerceSorting to a Spring Data {@link Sort} object.
     *
     * @return A {@link Sort} object representing the sorting criteria specified in this instance.
     */
    public Sort toSort(){
        return Sort.by(sorting.getDirection(),sorting.getSort());
    }
}
