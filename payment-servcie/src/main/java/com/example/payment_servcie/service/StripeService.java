package com.example.payment_servcie.service;

import com.example.payment_servcie.model.BookingEvent;
import com.example.payment_servcie.model.Payment;
import com.example.payment_servcie.model.PaymentStatusEvent;
import com.example.payment_servcie.repo.PaymentRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class StripeService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
   private PaymentRepo paymentRepo;

    @Value("${stripe.api.secret}")
    private String stripeSecret;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;
   
    @Value("${rabbitmq.exchange.name}")
    private String paymentResponseExchange;

    @Value("${rabbitmq.routing.key.name}")
    private String paymentResponseRoutingkey;



    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecret;
    }

    public String createCheckoutSession(BookingEvent bookingEvent) {
        try {
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:4200/success")
                    .setCancelUrl("http://localhost:4200/cancel")
                    .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity((long) bookingEvent.getSeatsBooked())
                                .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                            .setCurrency("usd")
                                            .setUnitAmount((long) (bookingEvent.getPrice() * 100))
                                            .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName("Flight " + bookingEvent.getFlightNumber())
                                                        .build()
                                            ).build()
                                ).build()
                    ).putMetadata("bookingId", String.valueOf(bookingEvent.getBookingId()))
                    .putMetadata("userEmail", bookingEvent.getUserEmail())
                    .build();

            Session session = Session.create(params);

            Payment payment = new Payment();
            payment.setBookingId(bookingEvent.getBookingId());
            payment.setAmount(bookingEvent.getPrice());
            payment.setStatus("PENDING");
            payment.setStripePaymentIntentId(session.getPaymentIntent());
            payment.setClientSecret(session.getId());
            payment.setPaymentTime(LocalDateTime.now());
            payment.setUserEmail(bookingEvent.getUserEmail());
            paymentRepo.save(payment);

            return session.getUrl();
        } catch (Exception e) {
            throw new RuntimeException("Stripe checkout creation failed", e);
        }
    }

    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request) throws Exception {
        String payload = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String sigHeader = request.getHeader("Stripe-Signature");

        Event event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

        if ("checkout.session.completed".equals(event.getType())) {
            Session session = (Session) event.getDataObjectDeserializer().getObject().get();
            Long bookingId = Long.parseLong(session.getMetadata().get("bookingId"));

            Payment payment = paymentRepo.findByBookingId(bookingId);
            if (payment != null) {
                payment.setStatus("CONFIRMED"); // update status here
                paymentRepo.save(payment); // save updated payment
            }

            // Send message to Booking Service after updating DB
            PaymentStatusEvent paymentStatus = new PaymentStatusEvent(bookingId, true);
            rabbitTemplate.convertAndSend(paymentResponseExchange, paymentResponseRoutingkey, paymentStatus);
        }

        return ResponseEntity.ok("Webhook received");
    }
    
}
