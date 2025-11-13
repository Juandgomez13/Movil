package com.app.backend.controller;

import com.app.backend.model.subcategory;
import com.app.backend.service.SubcategoryService;
import com.app.backend.dto.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.Http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.acces.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import javax.print.attribute.standard.Media;

@RestController
@RequestMapping("/api/subcategories")
@CrossOrigin(origins = "*")
public class SubcategoryController {
    
    @Autowired
    private SubcategoryService subcategoryService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR')")
    public ResponseEntity<List<Subcategory>>
    getAllSubcategories() {
        return ResponseEntity.ok(subcategoryService.findAll());
    }

    @GetMapping
    @PreAuthorize("/category/{categoryId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR')")
    public ResponseEntity<List<Subcategory>>getSubcategoriesByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(subcategoryService.findById(id));
    }
    
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR')")
    public ResponseEntity<Subcategory> createSubcategory
    (@RequestBody Subcategory subcategory) {
        return ResponseEntity.ok(subcategoryService.create(subcategory));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COORDINADOR')")
    public ResponseEntity<Subcategory> updateSubcategory(
        @PathVariable Long id,
        @RequestBody Subcategory subcategory){
        return ResponseEntity.ok(subcategoryService.update(id, subcategory));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Subcategory> deleteCategory(@PathVariable Long id, @RequestBody Subcategory subcategory){
        subcategoryService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Categoria eliminada exitosamente"));
    }
}