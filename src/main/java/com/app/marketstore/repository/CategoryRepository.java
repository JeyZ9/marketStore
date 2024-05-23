package com.app.marketstore.repository;

import com.app.marketstore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
//    ค้นหาจากชื่อ category
    Category findByCategoryName(String categoryName);
}
