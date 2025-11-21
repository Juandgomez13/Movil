package com.app.backend.service;

import com.app.backend.model.Product;
import com.app.backend.model.Category;
import com.app.backend.model.Subcategory;
import com.app.backend.dto.ProductRequest;
import com.app.backend.repository.ProductRepository;
import com.app.backend.repository.CategoryRepository;
import com.app.backend.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
        public void hardDelete(Long id) {
            Product product = findById(id);
            productRepository.delete(product);
        }
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private SubcategoryRepository subcategoryRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public List<Product> findByCategoryId(Long categoryId){
        return productRepository.findByCategoryIdAndActiveTrue(categoryId);
    }

    public List<Product> findBySubcategoryId(Long subcategoryId){
        return productRepository.findBySubcategoryIdAndActiveTrue(subcategoryId);
    }
    public Product findById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Product create(ProductRequest request){
        Category category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        
        Subcategory subcategory = subcategoryRepository.findById(request.getSubcategoryId())
            .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));
        
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setActive(request.getActive() != null ? request.getActive() : true);
        product.setCategory(category);
        product.setSubcategory(subcategory);
        
        return productRepository.save(product);
    }

    public Product update(Long id, ProductRequest request){
        Product product = findById(id);
        
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            product.setCategory(category);
        }
        
        if (request.getSubcategoryId() != null) {
            Subcategory subcategory = subcategoryRepository.findById(request.getSubcategoryId())
                .orElseThrow(() -> new RuntimeException("Subcategoría no encontrada"));
            product.setSubcategory(subcategory);
        }
        
        if (request.getName() != null) product.setName(request.getName());
        if (request.getDescription() != null) product.setDescription(request.getDescription());
        if (request.getPrice() != null) product.setPrice(request.getPrice());
        if (request.getStock() != null) product.setStock(request.getStock());
        if (request.getActive() != null) product.setActive(request.getActive());

        return productRepository.save(product);
    }

    public void delete(Long id){
        Product product = findById(id);
        product.setActive(false);
        productRepository.save(product);
    }

}
