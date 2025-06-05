package com.salonsync.service.impl;

import com.salonsync.model.Salon;
import com.salonsync.payload.dto.SalonDTO;
import com.salonsync.payload.dto.UserDTO;
import com.salonsync.service.SalonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalonServiceImpl implements SalonService {

    @Override
    public Salon createSalon(SalonDTO salon, UserDTO user) {
        return null;
    }

    @Override
    public Salon updateSalon(SalonDTO salon, UserDTO user, Long salonId) {
        return null;
    }

    @Override
    public List<Salon> getAllSalons() {
        return List.of();
    }

    @Override
    public Salon getSalonById(Long salonId) {
        return null;
    }

    @Override
    public Salon getSalonByOwnerId(Long ownerId) {
        return null;
    }

    @Override
    public List<Salon> getSalonByCity(String city) {
        return List.of();
    }
}
