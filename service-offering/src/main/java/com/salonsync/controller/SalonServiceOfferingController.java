package com.salonsync.controller;

import com.salonsync.dto.CategoryDTO;
import com.salonsync.dto.SalonDTO;
import com.salonsync.dto.ServiceDTO;
import com.salonsync.model.ServiceOffering;
import com.salonsync.service.ServiceOfferingService;
import com.salonsync.service.clients.CategoryFeignClient;
import com.salonsync.service.clients.SalonFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/service-offering/salon-owner")
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;
    private final SalonFeignClient salonFeignClient;
    private final CategoryFeignClient categoryFeignClient;

    @PostMapping
    public ResponseEntity<ServiceOffering> createService(
            @RequestBody ServiceDTO serviceDTO,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        SalonDTO salonDTO = (SalonDTO) salonFeignClient.getSalonByOwner(jwt).getBody();

        CategoryDTO categoryDTO = categoryFeignClient.getCategoriesByIdAndSalon(serviceDTO.getCategoryId(), salonDTO.getId()).getBody();
        categoryDTO.setId(serviceDTO.getCategoryId());

        ServiceOffering services = serviceOfferingService
                .createService(salonDTO, serviceDTO, categoryDTO);

        return ResponseEntity.ok(services);

    }

    @PostMapping("{id}")
    public ResponseEntity<ServiceOffering> updateService(
            @PathVariable Long id,
            @RequestBody ServiceOffering serviceOffering
    ) throws Exception {
        ServiceOffering services = serviceOfferingService
                .updateService(id, serviceOffering);
        return ResponseEntity.ok(services);

    }
}
