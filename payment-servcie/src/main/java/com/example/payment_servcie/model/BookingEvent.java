package com.example.payment_servcie.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingEvent {
  private Long bookingId;
  private String userEmail;
  private String flightNumber;
  private int seatsBooked;
  private Long flightId;
  private double price;
  private String status;
}