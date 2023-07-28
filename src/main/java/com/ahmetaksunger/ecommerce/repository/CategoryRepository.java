package com.ahmetaksunger.ecommerce.repository;

import com.ahmetaksunger.ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
