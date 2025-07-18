package com.salonsync.controller;

import com.salonsync.exception.UserException;
import com.salonsync.mapper.SalonMapper;
import com.salonsync.model.Salon;
import com.salonsync.payload.dto.SalonDTO;
import com.salonsync.payload.dto.UserDTO;
import com.salonsync.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salons")
@RequiredArgsConstructor
public class SalonController {

    private final SalonService salonService;
    private final com.salonsync.service.clients.UserFeignClient userFeignClient;

    @PostMapping
    public ResponseEntity<SalonDTO> createSalon(
            @RequestBody SalonDTO salonDTO,
            @RequestHeader("Authorization") String jwt
    ) throws UserException {
        UserDTO userDTO = userFeignClient.getUserFromJwtToken(jwt).getBody();
        Salon salon = salonService.createSalon(salonDTO, userDTO);
        SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO1);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SalonDTO> updateSalon(@PathVariable("id") Long salodId, @RequestBody SalonDTO salonDTO,@RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO userDTO = userFeignClient.getUserFromJwtToken(jwt).getBody();

        Salon salon = salonService.updateSalon(salonDTO, userDTO, salodId);
        SalonDTO salonDTO1 = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO1);
    }

    @GetMapping
    public ResponseEntity<List<SalonDTO>> getSalons() throws Exception {

        List<Salon> salons = salonService.getAllSalons();

        List<SalonDTO> salonDTOS = salons.stream().map((salon) ->
        {
            SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
            return salonDTO;
        }
        ).toList();

        return ResponseEntity.ok(salonDTOS);
    }

    @GetMapping("/{salonId}")
    public ResponseEntity<SalonDTO> getSalonById(
            @PathVariable("salonId") Long salonId
    ) throws Exception {

        Salon salon = salonService.getSalonById(salonId);

        SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO);

    }

    @GetMapping("/search")
    public ResponseEntity<List<SalonDTO>> searchSalons(
            @RequestParam("city") String city
    ) throws Exception {

        List<Salon> salons = salonService.searchSalonByCity(city);

        List<SalonDTO> salonDTOS = salons.stream().map((salon) ->
                {
                    SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
                    return salonDTO;
                }
        ).toList();

        return ResponseEntity.ok(salonDTOS);
    }

    @GetMapping("/owner")
    public ResponseEntity<SalonDTO> getSalonByOwnerId(

            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        UserDTO userDTO = userFeignClient.getUserFromJwtToken(jwt).getBody();

        if(userDTO == null) {
            throw new Exception("user not found");
        }

        Salon salon = salonService.getSalonByOwnerId(userDTO.getId());

        SalonDTO salonDTO = SalonMapper.mapToDTO(salon);
        return ResponseEntity.ok(salonDTO);

    }

}
