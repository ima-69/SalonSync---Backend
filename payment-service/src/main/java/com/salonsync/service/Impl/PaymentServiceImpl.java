package com.salonsync.service.Impl;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.salonsync.domain.PaymentMethod;
import com.salonsync.model.PaymentOrder;
import com.salonsync.payload.dto.BookingDTO;
import com.salonsync.payload.dto.UserDTO;
import com.salonsync.payload.response.PaymentLinkResponse;
import com.salonsync.repository.PaymentOrderRepository;
import com.salonsync.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentOrderRepository paymentOrderRepository;

    @Value("${stripe.api.key}")
    private String  stripeSecretKey;

    @Value("${razorpay.api.key}")
    private String razorpayApiKey;

    @Value("${razorpay.api.secret}")
    private String razorpayApiSecret;

    @Override
    public PaymentLinkResponse createOrder(
            UserDTO user,
            BookingDTO booking,
            PaymentMethod paymentMethod
    ) throws RazorpayException {

        Long amount = (long) booking.getTotalPrice();
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentMethod(paymentMethod);
        paymentOrder.setBookingId(booking.getId());
        paymentOrder.setSalonId(booking.getSalonId());
        PaymentOrder savedPaymentOrder = paymentOrderRepository.save(paymentOrder);

        PaymentLinkResponse response = new PaymentLinkResponse();

        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            PaymentLink payment = createRazorPayPaymentLink(
                    user,
                    savedPaymentOrder.getAmount(),
                    savedPaymentOrder.getId()
            );

            String paymentUrl = payment.get("short_url");
            String paymentUrlId = payment.get("id");

            response.setPayment_link_url(paymentUrl);
            response.setPayment_link_id(paymentUrlId);

            savedPaymentOrder.setPaymentLinkId(paymentUrlId);

            paymentOrderRepository.save(savedPaymentOrder);
        } else {
            String paymentUrl = createStripePaymentLink(
                    user,
                    savedPaymentOrder.getAmount(),
                    savedPaymentOrder.getId()
            );
            response.setPayment_link_url(paymentUrl);
        }
        return response;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        PaymentOrder paymentOrder = paymentOrderRepository.findById(id).orElse(null);
        if(paymentOrder == null){
            throw new Exception("payment order not found");
        }
        return paymentOrder;
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {
        return paymentOrderRepository.findByPaymentLinkId(paymentId);
    }

    @Override
    public PaymentLink createRazorPayPaymentLink(
            UserDTO user,
            Long Amount,
            Long orderId
    ) throws RazorpayException {
        Long amount = Amount * 100;

        RazorpayClient razorpay = new RazorpayClient(razorpayApiKey, razorpayApiSecret);

        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",amount);
        paymentLinkRequest.put("currency", "INR");

        JSONObject customer = new JSONObject();
        customer.put("firstName", user.getFirstName());
        customer.put("lastName", user.getLastName());
        customer.put("email", user.getEmail());

        paymentLinkRequest.put("customer", customer);

        JSONObject notify = new JSONObject();
        notify.put("email", true);

        paymentLinkRequest.put("notify", notify);

        paymentLinkRequest.put("reminder_enable", true);

        paymentLinkRequest.put("callback_url", "http://localhost:3000/payment-success/"+orderId);

        paymentLinkRequest.put("callback_mrthod", "get");

        return razorpay.paymentLink.create(paymentLinkRequest);
    }

    @Override
    public String createStripePaymentLink(UserDTO user, Long amount, Long orderId) {
        return "";
    }
}
