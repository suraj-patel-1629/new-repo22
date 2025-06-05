package com.example.Check_in_service.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingEvent {
  private Long bookingId;
  private String userEmail;
  private String flightNumber;
  private int seatsBooked;
  private Long flightId;
  private double price;
  private String status;
}
