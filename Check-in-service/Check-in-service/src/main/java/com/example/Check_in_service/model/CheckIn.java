package com.example.Check_in_service.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckIn {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long bookingId;
  private String userEmail;
  private String flightNumber;
  private Long flightId;
  private int seatsBooked;
  private double price;
  private String status;
  private String seatNumber;
  private LocalDateTime checkInTime;
  private boolean checkedIn;
  
  
}
