package com.salonsync.repository;

import com.salonsync.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Set<Category> findBySalonId(Long salonId);

    Category findByIdAndSalonId(Long id, Long salonId);
}
