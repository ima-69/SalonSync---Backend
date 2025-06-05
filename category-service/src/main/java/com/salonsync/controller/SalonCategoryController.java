package com.salonsync.controller;

import com.salonsync.dto.SalonDTO;
import com.salonsync.model.Category;
import com.salonsync.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories/salon-owner")
public class SalonCategoryController {

    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<Category> createCategory(
            @RequestBody Category category
    ) {
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);
        Category newCategory = categoryService.createCategory(category, salonDTO);
        return ResponseEntity.ok(newCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(
            @PathVariable Long id
    ) throws Exception {
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);
        categoryService.deleteCategoryById(id, salonDTO.getId());
        return ResponseEntity.ok("category deleted successfully");
    }

}