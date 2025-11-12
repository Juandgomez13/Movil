package com.app.backend.service;

import com.app.backend.model.SubCategory;
import com.app.backend.repository.SubCategoryRepository;
import com.app.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Locale.Category;

@Service
public class SubCategoryService {
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<SubCategory> findAll(){
        return subCategoryRepository.findAll();
    }

    public SubCategory findById(Long id){
        return subCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Subcategoria no encontrada"));
    }

    public SubCategory create(SubCategory subCategory){
        return subCategoryRepository.save(subCategory);
    }

    public SubCategory update(Long id, subCategory subCategoryDetails){
        SubCategory subCategory = findById(id);
        subCategory.setName(subCategoryDetails.getName());
        subCategory.setDescription(subCategoryDetails.getDescription());
        subCategory.setActive(subCategoryDetails.getActive());
        subCategory.setCategory(subCategoryDetails.getCategory());

        return subCategoryRepository.save(subCategory);
    }

    public void delete(Long id){
        SubCategory subCategory = findById(id);
        subCategoryRepository.delete(subCategory);
    }
}
