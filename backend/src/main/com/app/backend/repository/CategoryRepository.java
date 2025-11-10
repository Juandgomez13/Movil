package com.app.backend.repository;

import com.app.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository <Category, Long> {
    Optional <category> findByname(String name);
    Boolean existsByname(String name);
}
