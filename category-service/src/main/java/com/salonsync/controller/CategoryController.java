package com.salonsync.controller;

import com.salonsync.model.Category;
import com.salonsync.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<Set<Category>> getAllCategoriesBySalonId(
            @PathVariable Long id
    ) {
        Set<Category> categories = categoryService.getAllCategoriesBySalonId(id);
        return ResponseEntity.ok(categories);
    }
}
