package com.example.payment_servcie.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatusEvent {
  private Long bookingId;
  private boolean paymentSuccessfull;

}
