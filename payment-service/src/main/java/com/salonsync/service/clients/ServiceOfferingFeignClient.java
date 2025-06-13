package com.salonsync.service.clients;

import com.salonsync.dto.ServiceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient("SERVICE-OFFERING")
public interface ServiceOfferingFeignClient {

    @GetMapping("/api/service-offering/list/{ids}")
    public ResponseEntity<ServiceDTO> getServicesByIds(
            @PathVariable Set<Long> ids
    );
}
