package com.app.backend.repository;

import com.app.backend.model.Product;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long>{
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findBySubcategoryId(Long subcategoryId);
}
