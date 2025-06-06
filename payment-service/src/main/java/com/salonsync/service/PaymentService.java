package com.salonsync.service;

import com.razorpay.PaymentLink;
import com.salonsync.domain.PaymentMethod;
import com.salonsync.model.PaymentOrder;
import com.salonsync.payload.dto.BookingDTO;
import com.salonsync.payload.dto.UserDTO;
import com.salonsync.payload.response.PaymentLinkResponse;

public interface PaymentService {

    PaymentLinkResponse createOrder(
            UserDTO user,
            BookingDTO booking,
            PaymentMethod paymentMethod
    );

    PaymentOrder getPaymentOrderById(Long id);

    PaymentOrder getPaymentOrderByPaymentId(String paymentId);

    PaymentLink createRazorPayPaymentLink(
            UserDTO user,
            Long amount,
            Long orderId
    );

    String createStripePaymentLink(
            UserDTO user,
            Long amount,
            Long orderId
    );
}
