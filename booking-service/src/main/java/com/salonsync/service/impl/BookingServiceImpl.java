package com.salonsync.service.impl;

import com.salonsync.domain.BookingStatus;
import com.salonsync.dto.BookingRequest;
import com.salonsync.dto.SalonDTO;
import com.salonsync.dto.ServiceDTO;
import com.salonsync.dto.UserDTO;
import com.salonsync.modal.Booking;
import com.salonsync.modal.PaymentOrder;
import com.salonsync.modal.SalonReport;
import com.salonsync.repository.BookingRepository;
import com.salonsync.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public Booking createBooking(BookingRequest booking,
                                 UserDTO userDTO,
                                 SalonDTO salonDTO,
                                 Set<ServiceDTO> serviceDTOSet
    ) throws Exception {
        int totalDuration = serviceDTOSet.stream()
                .mapToInt(ServiceDTO::getDuration)
                .sum();

        LocalDateTime bookingStartTime = booking.getStartTime();
        LocalDateTime bookingEndTime = bookingStartTime.plusMinutes(totalDuration);

        Boolean isSlotAvailable = isTimeAvailable(salonDTO, bookingStartTime, bookingEndTime);

        int totalPrice = serviceDTOSet.stream()
                .mapToInt(ServiceDTO::getPrice)
                .sum();

        Set<Long> idList = serviceDTOSet.stream()
                .map(ServiceDTO::getId)
                .collect(Collectors.toSet());

        Booking newBooking = new Booking();
        newBooking.setCustomerId(userDTO.getId());
        newBooking.setSalonId(salonDTO.getId());
        newBooking.setServiceIds(idList);
        newBooking.setStatus(BookingStatus.PENDING);
        newBooking.setStartTime(bookingStartTime);
        newBooking.setEndTime(bookingEndTime);
        newBooking.setTotalPrice(totalPrice);

        return bookingRepository.save(newBooking);
    }

    public Boolean isTimeAvailable(
            SalonDTO salonDTO,
            LocalDateTime bookingStartTime,
            LocalDateTime bookingEndTime
    ) throws Exception {

        List<Booking> existingBookings = getBookingsBySalon(salonDTO.getId());

        LocalDateTime salonOpenTime = salonDTO.getOpenTime().atDate(bookingStartTime.toLocalDate());
        LocalDateTime salonCloseTime = salonDTO.getCloseTime().atDate(bookingEndTime.toLocalDate());

        if(bookingStartTime.isBefore(salonOpenTime) || bookingEndTime.isAfter(salonCloseTime) ){
            throw new Exception("Booking time must be within salon's working hours");
        }

        for(Booking existingBooking : existingBookings){
            LocalDateTime existingStartTime = existingBooking.getStartTime();
            LocalDateTime existingEndTime = existingBooking.getEndTime();

            if(bookingStartTime.isBefore(existingEndTime) && bookingEndTime.isAfter(existingStartTime)){
                throw new Exception("Booking time overlaps with another booking, choose another time");
            }

            if(bookingStartTime.isEqual(existingStartTime) || bookingEndTime.isEqual(existingEndTime)){
                throw new Exception("Booking time overlaps with another booking, choose another time");
            }
        }

        return true;
    }

    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }

    @Override
    public List<Booking> getBookingsBySalon(Long salonId) {
        return bookingRepository.findBySalonId(salonId);
    }

    @Override
    public Booking getBookingById(Long id) throws Exception {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if(booking == null){
            throw new Exception("booking not found");
        }
        return booking;
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingStatus status) throws Exception {

        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingsByDate(LocalDate date, Long salonId) {
        return List.of();
    }

    @Override
    public SalonReport getSalonReport(Long salonId) {

        List<Booking> bookings = getBookingsBySalon(salonId);

        int totalEarnings = bookings.stream()
                .mapToInt(Booking::getTotalPrice)
                .sum();

        Integer totalBookings = bookings.size();

        List<Booking> cancelledBookings = bookings.stream()
                .filter(booking -> booking.getStatus().equals(BookingStatus.CANCELLED))
                .collect(Collectors.toList());

        Double totalRefund = cancelledBookings.stream()
                .mapToDouble(Booking::getTotalPrice)
                .sum();

        SalonReport salonReport = new SalonReport();
        salonReport.setSalonId(salonId);
        salonReport.setCancelledBookings(cancelledBookings.size());
        salonReport.setTotalBookings(totalBookings);
        salonReport.setTotalEarnings(totalEarnings);
        salonReport.setTotalRefund(totalRefund);

        return salonReport;
    }

    @Override
    public Booking bookingSucess(PaymentOrder order) throws Exception {
        Booking existingBooking = getBookingById(order.getBookingId());
        existingBooking.setStatus(BookingStatus.CONFIRMED);
        return bookingRepository.save(existingBooking);
    }
}
