package com.salonsync.controller;

import com.salonsync.dto.CategoryDTO;
import com.salonsync.dto.SalonDTO;
import com.salonsync.dto.ServiceDTO;
import com.salonsync.model.ServiceOffering;
import com.salonsync.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @PostMapping
    public ResponseEntity<ServiceOffering> createService(
            @RequestBody ServiceDTO serviceDTO
    ) {
        SalonDTO salonDTO = new SalonDTO();
        salonDTO.setId(1L);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(serviceDTO.getCategoryId());

        ServiceOffering services = serviceOfferingService
                .createService(salonDTO, serviceDTO, categoryDTO);
        return ResponseEntity.ok(services);

    }
}
