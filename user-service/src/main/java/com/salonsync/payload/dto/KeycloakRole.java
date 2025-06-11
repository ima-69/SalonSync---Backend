package com.salonsync.payload.dto;

import lombok.Data;

import java.util.Map;

@Data
public class KeycloakRole {
    private String id;
    private String name;
    private String description;
    private Boolean composite;
    private Boolean clientRole;
    private Boolean containerId;
    private Map<String, Object> attributes;
}
