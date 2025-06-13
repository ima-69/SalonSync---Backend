package com.salonsync.controller;

import com.salonsync.dto.SalonDTO;
import com.salonsync.model.Category;
import com.salonsync.service.CategoryService;
import com.salonsync.service.clients.SalonFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories/salon-owner")
public class SalonCategoryController {

    private final CategoryService categoryService;
    private final SalonFeignClient salonFeignClient;

    @PostMapping()
    public ResponseEntity<Category> createCategory(
            @RequestBody Category category,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        SalonDTO salonDTO = salonFeignClient.getSalonByOwner(jwt).getBody();
        Category newCategory = categoryService.createCategory(category, salonDTO);
        return ResponseEntity.ok(newCategory);
    }

    @GetMapping("/salon/{salonId}/category/{id}")
    public ResponseEntity<Category> getCategoriesByIdAndSalon(
            @PathVariable Long id,
            @PathVariable Long salonId
    ) throws Exception {
        Category categories = categoryService.findByIdAndSalonId(id, salonId);
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategoryById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        SalonDTO salonDTO = salonFeignClient.getSalonByOwner(jwt).getBody();
        categoryService.deleteCategoryById(id, salonDTO.getId());
        return ResponseEntity.ok("category deleted successfully");
    }

}