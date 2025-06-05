package com.salonsync.service.impl;

import com.salonsync.dto.SalonDTO;
import com.salonsync.model.Category;
import com.salonsync.repository.CategoryRepository;
import com.salonsync.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category, SalonDTO salonDTO) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setSalonId(salonDTO.getId());
        newCategory.setImage(category.getImage());
        return categoryRepository.save(newCategory);
    }

    @Override
    public Set<Category> getAllCategoriesBySalonId(Long salonId) {
        return categoryRepository.findBySalonId(salonId);
    }

    @Override
    public Category getCategoryById(Long categoryId) throws Exception {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if(category == null) {
            throw new Exception("category not exist with id " + categoryId);
        }
        return category;
    }

    @Override
    public void deleteCategoryById(Long categoryId, Long salonId) throws Exception {
         Category category = getCategoryById(categoryId);
         if(!category.getSalonId().equals(salonId)) {
             throw new Exception("You don't have permission to delete this category");
         }
         categoryRepository.deleteById(categoryId);
    }
}
