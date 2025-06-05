package com.salonsync.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {

    private Long id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private Long salonId;
    private Long categoryId;
    private String image;

}
