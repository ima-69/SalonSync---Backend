package com.salonsync.controller;

import com.razorpay.RazorpayException;
import com.salonsync.domain.PaymentMethod;
import com.salonsync.exception.UserException;
import com.salonsync.model.PaymentOrder;
import com.salonsync.payload.dto.BookingDTO;
import com.salonsync.payload.dto.UserDTO;
import com.salonsync.payload.response.PaymentLinkResponse;
import com.salonsync.service.PaymentService;
import com.salonsync.service.clients.UserFeignClient;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final UserFeignClient userFeignClient;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @RequestBody BookingDTO booking,
            @RequestParam PaymentMethod paymentMethod,
            @RequestHeader("Authorization") String jwt
    ) throws StripeException, RazorpayException, UserException {

        UserDTO user = userFeignClient.getUserFromJwtToken(jwt).getBody();

        PaymentLinkResponse res = paymentService.createOrder(user, booking, paymentMethod);

        return ResponseEntity.ok(res);
    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(
            @PathVariable Long paymentOrderId
    ) throws Exception {

        PaymentOrder res = paymentService.getPaymentOrderById(paymentOrderId);
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/proceed")
    public ResponseEntity<Boolean> processPayment(
            @RequestParam String paymentId,
            @RequestParam String paymentLinkId
    ) throws Exception {

        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);

        Boolean res = paymentService.proceedPayment(
                paymentOrder,
                paymentId,
                paymentLinkId
                );
        return ResponseEntity.ok(res);
    }
}
