package com.example.payment_servcie.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.payment_servcie.model.BookingEvent;
import com.example.payment_servcie.model.Payment;
import com.example.payment_servcie.service.PaymentService;
import com.example.payment_servcie.service.StripeService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/payment")
public class PaymentController {

  @Autowired
  private PaymentService paymentservice;

  @Autowired
   private  StripeService stripeService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody BookingEvent bookingEvent) {
        String checkoutUrl = stripeService.createCheckoutSession(bookingEvent);
        Map<String, String> response = new HashMap<>();
        response.put("checkoutUrl", checkoutUrl);
        return ResponseEntity.ok(response);
    }

 @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request) throws Exception {
        return stripeService.handleStripeWebhook(request);
    }

  @GetMapping("/paymentbyid")
  public ResponseEntity<Payment> paymentDetail(@RequestParam Long id) {
    return ResponseEntity.ok(paymentservice.getPaymentDetail(id));
  }

  @GetMapping("/getallpayment")
  public ResponseEntity<List<Payment>> allPaymentDetails() {
    List<Payment> details = paymentservice.getAllPaymentDetail();
    return ResponseEntity.ok(details);
  }
}
