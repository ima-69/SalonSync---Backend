package com.salonsync.service;

import com.salonsync.dto.CategoryDTO;
import com.salonsync.dto.SalonDTO;
import com.salonsync.dto.ServiceDTO;
import com.salonsync.model.ServiceOffering;

import java.util.List;
import java.util.Set;

public interface ServiceOfferingService {

     ServiceOffering createService(
             SalonDTO salonDTO,
             ServiceDTO serviceDTO,
             CategoryDTO categoryDTO
     );

     ServiceOffering updateService(Long salonId, ServiceOffering service);

     Set<ServiceOffering> getAllServicesBySalonId(Long salonId, Long categoryId);

     Set<ServiceOffering> getServicesByIds(Set<Long> ids);
}
