package com.salonsync.service.impl;

import com.salonsync.dto.CategoryDTO;
import com.salonsync.dto.SalonDTO;
import com.salonsync.dto.ServiceDTO;
import com.salonsync.model.ServiceOffering;
import com.salonsync.repository.ServiceOfferingRepository;
import com.salonsync.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ServiceOfferingServiceImpl implements ServiceOfferingService {

    private final ServiceOfferingRepository serviceOfferingRepository;

    @Override
    public ServiceOffering createService(SalonDTO salonDTO,
                                         ServiceDTO serviceDTO,
                                         CategoryDTO categoryDTO)
    {
        ServiceOffering serviceOffering = new ServiceOffering();
        serviceOffering.setImage(serviceDTO.getImage());
        serviceOffering.setSalonId(salonDTO.getId());
        serviceOffering.setName(serviceDTO.getName());
        serviceOffering.setDescription(serviceDTO.getDescription());
        serviceOffering.setCategoryId(categoryDTO.getId());
        serviceOffering.setPrice(serviceDTO.getPrice());
        serviceOffering.setDuration(serviceDTO.getDuration());

        return serviceOfferingRepository.save(serviceOffering);
    }

    @Override
    public ServiceOffering updateService(Long salonId, ServiceOffering service) {
        return null;
    }

    @Override
    public Set<ServiceOffering> getAllServicesBySalonId(Long salonId, Long categoryId) {
        return Set.of();
    }

    @Override
    public Set<ServiceOffering> getServicesByIds(Set<Long> ids) {
        return Set.of();
    }
}
