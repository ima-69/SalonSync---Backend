package com.salonsync.model;

import jakarta.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = false)
    private String name;

    private String image;

    private Long salonId;
}
