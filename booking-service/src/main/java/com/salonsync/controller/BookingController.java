package com.salonsync.controller;

import com.salonsync.domain.BookingStatus;
import com.salonsync.domain.PaymentMethod;
import com.salonsync.dto.*;
import com.salonsync.exception.UserException;
import com.salonsync.mapper.BookingMapper;
import com.salonsync.modal.Booking;
import com.salonsync.modal.SalonReport;
import com.salonsync.service.BookingService;
import com.salonsync.service.clients.PaymentFeignClient;
import com.salonsync.service.clients.SalonFeignClient;
import com.salonsync.service.clients.ServiceOfferingFeignClient;
import com.salonsync.service.clients.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final SalonFeignClient salonFeignClient;
    private final UserFeignClient userFeignClient;
    private final ServiceOfferingFeignClient serviceOfferingFeignClient;
    private final PaymentFeignClient paymentFeignClient;

    @PostMapping
    public ResponseEntity<PaymentLinkResponse> createBooking(
            @RequestParam Long salonId,
            @RequestParam PaymentMethod paymentMethod,
            @RequestBody BookingRequest bookingRequest,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        UserDTO userDTO = userFeignClient.getUserFromJwtToken(jwt).getBody();

        SalonDTO salonDTO = salonFeignClient.getSalonById(salonId).getBody();

        Set<ServiceDTO> serviceDTOSet = serviceOfferingFeignClient.getServicesByIds(bookingRequest.getServiceIds()).getBody();

        if(serviceDTOSet == null || serviceDTOSet.isEmpty()){
            throw new Exception("No services found for the selected services");
        }

        Booking booking = bookingService.createBooking(
                bookingRequest,
                userDTO,
                salonDTO,
                serviceDTOSet
        );

        BookingDTO bookingDTO = BookingMapper.toDTO(booking);

        PaymentLinkResponse res = paymentFeignClient.createPaymentLink(
                bookingDTO,
                paymentMethod,
                jwt
                ).getBody();

        return ResponseEntity.ok(res);
    }

    @GetMapping("/customer")
    public ResponseEntity<Set< BookingDTO>> getBookingByCustomer(
        @RequestHeader("Authorization") String jwt
    ) throws Exception {
        com.salonsync.payload.dto.UserDTO userDTO = userFeignClient.getUserFromJwtToken(jwt).getBody();

        if(userDTO == null){
            throw new Exception("user not found from jwt token");
        }

        List<Booking> bookings = bookingService.getBookingsByCustomer(userDTO.getId());

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }

    @GetMapping("/salon")
    public ResponseEntity<Set< BookingDTO>> getBookingBySalon(
        @RequestHeader("Authorization") String jwt
    ) throws Exception{

        SalonDTO salonDTO = salonFeignClient.getSalonById(jwt).getBody();
        List<Booking> bookings = bookingService.getBookingsBySalon(salonDTO.getId());

        return ResponseEntity.ok(getBookingDTOs(bookings));
    }



    private Set<BookingDTO> getBookingDTOs(List<Booking> bookings) {
        return bookings.stream()
                .map(booking -> {
                    return BookingMapper.toDTO(booking);
                }).collect(Collectors.toSet());
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> getBookingById(
            @PathVariable Long bookingId
    ) throws Exception {
        Booking booking = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @PutMapping("/{bookingId}/status")
    public ResponseEntity<BookingDTO> updateBookingStatus(
            @PathVariable Long bookingId,
            @RequestParam BookingStatus status
    ) throws Exception {
        Booking booking = bookingService.updateBooking(bookingId, status);
        return ResponseEntity.ok(BookingMapper.toDTO(booking));
    }

    @GetMapping("/slots/salon/{salonId}/date/{date}")
    public ResponseEntity<List<BookingSlotDTO>> getBookedSlot(
            @PathVariable Long salonId,
            @RequestParam LocalDate date
            ) throws Exception {
        List<Booking> bookings = bookingService.getBookingsByDate(date, salonId);

        List<BookingSlotDTO> slotDTOS = bookings.stream()
                .map(booking -> {
                    BookingSlotDTO slotDTO = new BookingSlotDTO();
                    slotDTO.setStartTime(booking.getStartTime());
                    slotDTO.setEndTime(booking.getEndTime());
                    return slotDTO;
                }).collect(Collectors.toList());

        return ResponseEntity.ok(slotDTOS);
    }

    @GetMapping("/report")
    public ResponseEntity<SalonReport> getSalonReport(
        @RequestHeader("Authorization") String jwt
    ) throws Exception {

        SalonDTO salonDTO = salonFeignClient.getSalonById(jwt).getBody();
        SalonReport report = bookingService.getSalonReport(salonDTO.getId());
        return ResponseEntity.ok(report);
    }

}
