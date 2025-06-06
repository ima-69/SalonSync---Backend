package com.salonsync.service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.salonsync.domain.PaymentMethod;
import com.salonsync.model.PaymentOrder;
import com.salonsync.payload.dto.BookingDTO;
import com.salonsync.payload.dto.UserDTO;
import com.salonsync.payload.response.PaymentLinkResponse;
import com.stripe.exception.StripeException;

public interface PaymentService {

    PaymentLinkResponse createOrder(
            UserDTO user,
            BookingDTO booking,
            PaymentMethod paymentMethod
    ) throws RazorpayException, StripeException;

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentOrderByPaymentId(String paymentId);

    PaymentLink createRazorPayPaymentLink(
            UserDTO user,
            Long amount,
            Long orderId
    ) throws RazorpayException;

    String createStripePaymentLink(
            UserDTO user,
            Long amount,
            Long orderId
    ) throws StripeException;
}
