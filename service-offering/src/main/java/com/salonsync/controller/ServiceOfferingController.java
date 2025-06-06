package com.salonsync.controller;

import com.salonsync.model.ServiceOffering;
import com.salonsync.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/service-offering")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<ServiceOffering>> getServicesBySalonId(
            @PathVariable Long salonId,
            @RequestParam(required = false) Long categoryId
    ) {
        Set<ServiceOffering> services = serviceOfferingService.getAllServicesBySalonId(salonId, categoryId);
        return ResponseEntity.ok(services);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceOffering> getServiceById(
            @PathVariable Long id
    ) throws Exception {
        ServiceOffering services = serviceOfferingService
                .getServiceById(id);
        return ResponseEntity.ok(services);
    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<Set<ServiceOffering>> getServicesByIds(
            @PathVariable Set<Long> ids
    ) {
        Set<ServiceOffering> services = serviceOfferingService
                .getServicesByIds(ids);
        return ResponseEntity.ok(services);

    }

}
