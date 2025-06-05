package com.example.payment_servcie.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long bookingId;
  private String stripePaymentIntentId;
  private double amount;
  private String status;
  private LocalDateTime paymentTime;
  private String clientSecret;
  private String userEmail;
}
