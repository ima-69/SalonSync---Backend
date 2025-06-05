package com.salonsync.service.impl;

import com.salonsync.dto.CategoryDTO;
import com.salonsync.dto.SalonDTO;
import com.salonsync.dto.ServiceDTO;
import com.salonsync.model.ServiceOffering;
import com.salonsync.repository.ServiceOfferingRepository;
import com.salonsync.service.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    public ServiceOffering updateService(
            Long serviceId,
            ServiceOffering service
    ) throws Exception {
        ServiceOffering existingService = serviceOfferingRepository.findById(serviceId).orElse(null);
        if(existingService == null) {
            throw new Exception("service not exist with id "+serviceId);
        }
        existingService.setImage(service.getImage());
        existingService.setName(service.getName());
        existingService.setDescription(service.getDescription());
        existingService.setPrice(service.getPrice());
        existingService.setDuration(service.getDuration());

        return serviceOfferingRepository.save(existingService);
    }

    @Override
    public Set<ServiceOffering> getAllServicesBySalonId(Long salonId, Long categoryId) {
        Set<ServiceOffering> services = serviceOfferingRepository.findBySalonId(salonId);
        if(categoryId != null) {
            services = services.stream().filter((service)->service.getCategoryId() != null &&
                    service.getCategoryId()==categoryId).collect(Collectors.toSet());
        }
        return services;
    }

    @Override
    public Set<ServiceOffering> getServicesByIds(Set<Long> ids) {
        List<ServiceOffering> services = serviceOfferingRepository.findAllById(ids);
        return new HashSet<>(services);
    }
}
