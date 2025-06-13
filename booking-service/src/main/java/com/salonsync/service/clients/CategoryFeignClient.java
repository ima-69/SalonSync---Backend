package com.salonsync.service.clients;

import com.salonsync.dto.CategoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("CATEGORY")
public interface CategoryFeignClient {

//    @GetMapping("/api/categories/{id}")
//    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) throws Exception;

    @GetMapping("api/categories/salon-owner/salon/{salonId}/category/{id}")
    public ResponseEntity<CategoryDTO> getCategoriesByIdAndSalon(
            @PathVariable Long id,
            @PathVariable Long salonId
    ) throws Exception;
}
