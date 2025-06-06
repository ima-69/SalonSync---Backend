package com.salonsync.service.Impl;

import com.razorpay.PaymentLink;
import com.salonsync.domain.PaymentMethod;
import com.salonsync.model.PaymentOrder;
import com.salonsync.payload.dto.BookingDTO;
import com.salonsync.payload.dto.UserDTO;
import com.salonsync.payload.response.PaymentLinkResponse;
import com.salonsync.repository.PaymentOrderRepository;
import com.salonsync.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentOrderRepository paymentOrderRepository;

    @Override
    public PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) {
        return null;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) {
        return null;
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {
        return null;
    }

    @Override
    public PaymentLink createRazorPayPaymentLink(UserDTO user, Long amount, Long orderId) {
        return null;
    }

    @Override
    public String createStripePaymentLink(UserDTO user, Long amount, Long orderId) {
        return "";
    }
}
