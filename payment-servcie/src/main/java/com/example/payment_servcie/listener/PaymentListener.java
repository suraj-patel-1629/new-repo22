// package com.example.payment_servcie.listener;



// import java.time.LocalDateTime;
// import java.util.logging.Logger;

// import org.slf4j.LoggerFactory;
// import org.springframework.amqp.rabbit.annotation.RabbitListener;
// import org.springframework.amqp.rabbit.core.RabbitTemplate;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;

// import com.example.payment_servcie.model.BookingEvent;
// import com.example.payment_servcie.model.Payment;
// import com.example.payment_servcie.model.PaymentStatusEvent;
// import com.example.payment_servcie.repo.PaymentRepo;
// import com.stripe.Stripe;
// import com.stripe.exception.StripeException;
// import com.stripe.model.PaymentIntent;
// import com.stripe.param.PaymentIntentCreateParams;
// import com.example.payment_servcie.service.StripeService;
// import jakarta.annotation.PostConstruct;

// @Service
// public class PaymentListener {

//   @Autowired
//   private RabbitTemplate rabbitTemplate;

//   @Autowired
//   private PaymentRepo paymentRepo;

//   @Autowired
//   private StripeService stripeService;

//   @Value("${rabbitmq.exchange.name}")
//   private String paymentResponseExchange;

//   @Value("${rabbitmq.routing.key.name}")
//   private String paymentResponseRoutingkey;

//   @Value("${stripe.secret.key}")
//   private String stripeSecretKey;

 
//   org.slf4j.Logger logger = LoggerFactory.getLogger(PaymentListener.class);

//   @RabbitListener(queues = { "${rabbitmq.queue.name3}" })
//   public void processPayment(BookingEvent bookingEvent) {
   
   
//     try {
//       PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
//           .setAmount((long) (bookingEvent.getPrice() * 100)) // Stripe needs amount in cents
//           .setCurrency("inr")
//           .setReceiptEmail(bookingEvent.getUserEmail())
//           .build();

//       PaymentIntent intent = PaymentIntent.create(params);

//       Payment payment = new Payment();
//       payment.setBookingId(bookingEvent.getBookingId());
//       payment.setStripePaymentIntentId(intent.getId());
//       payment.setAmount(bookingEvent.getPrice());
//       payment.setStatus(intent.getStatus());
//       payment.setPaymentTime(LocalDateTime.now());
//       payment.setClientSecret(intent.getClientSecret());
//       payment.setUserEmail(bookingEvent.getUserEmail());

//       paymentRepo.save(payment);

//       boolean success = "succeeded".equalsIgnoreCase(intent.getStatus());
//       PaymentStatusEvent event = new PaymentStatusEvent(bookingEvent.getBookingId(), success);
      
//       rabbitTemplate.convertAndSend(paymentResponseExchange, paymentResponseRoutingkey, event);
//     } catch (StripeException e) {
//       throw new RuntimeException("Payment processing failed: " + e.getMessage());
      
//     }

   
  

//   }

// }
