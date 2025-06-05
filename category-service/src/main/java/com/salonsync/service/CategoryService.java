package com.salonsync.service;

import com.salonsync.dto.SalonDTO;
import com.salonsync.model.Category;

import java.util.Set;

public interface CategoryService {

    Category createCategory(Category category, SalonDTO salonDTO);
    Set<Category> getAllCategoriesBySalonId(Long salonId);
    Category getCategoryById(Long categoryId) throws Exception;
    void deleteCategoryById(Long categoryId, Long salonId) throws Exception;
}
