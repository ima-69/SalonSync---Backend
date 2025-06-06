package com.salonsync.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingRequest {
     private LocalDateTime startTime;
     private LocalDateTime endTime;
     private Set<Long> serviceIds;
}
