package com.app.backend.service;

import com.app.backend.model.Subcategory;
import com.app.backend.model.Category;
import com.app.backend.dto.SubcategoryRequest;
import com.app.backend.repository.SubcategoryRepository;
import com.app.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubCategoryService {
            public List<Subcategory> findAllWithInactive(){
                return subcategoryRepository.findAll();
            }
        public void hardDelete(Long id) {
            Subcategory subcategory = findById(id);
            subcategoryRepository.delete(subcategory);
        }
    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Subcategory> findAll(){
        return subcategoryRepository.findAll();
    }

    public Subcategory findById(Long id){
        return subcategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Subcategoria no encontrada"));
    }

    public Subcategory create(SubcategoryRequest request){
        Category category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        
        Subcategory subcategory = new Subcategory();
        subcategory.setName(request.getName());
        subcategory.setDescription(request.getDescription());
        subcategory.setCategory(category);
        subcategory.setActive(request.getActive());
        
        return subcategoryRepository.save(subcategory);
    }

    public Subcategory update(Long id, SubcategoryRequest request){
        Subcategory subcategory = findById(id);
        
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            subcategory.setCategory(category);
        }
        
        if (request.getName() != null) subcategory.setName(request.getName());
        if (request.getDescription() != null) subcategory.setDescription(request.getDescription());
        if (request.getActive() != null) subcategory.setActive(request.getActive());

        return subcategoryRepository.save(subcategory);
    }

    public void delete(Long id){
        Subcategory subcategory = findById(id);
        subcategory.setActive(false);
        subcategoryRepository.save(subcategory);
    }

    public List<Subcategory> findByCategoryId(Long categoryId){
        return subcategoryRepository.findByCategoryIdAndActiveTrue(categoryId);
    }
}
