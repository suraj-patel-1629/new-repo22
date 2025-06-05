package com.example.Check_in_service.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.Check_in_service.model.CheckIn;
import com.example.Check_in_service.repo.CheckInRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckInService {
  private final CheckInRepository checkInRepo;

  public CheckIn doCheckIn(Long bookingId) {
    CheckIn checkIn = checkInRepo.findByBookingId(bookingId)
        .orElseThrow(() -> new RuntimeException("No booking found for check-in"));

    if (checkIn.isCheckedIn()) {
      log.warn(" Booking ID {} already checked in.", bookingId);
      return checkIn;
    }

    checkIn.setCheckedIn(true);
    checkIn.setCheckInTime(LocalDateTime.now());
    checkIn.setSeatNumber(generateRandomSeat());
    CheckIn updated = checkInRepo.save(checkIn);
    log.info(" Check-in successful for Booking ID: {}", bookingId);
    return updated;
  }

  public Optional<CheckIn> getCheckInStatus(Long bookingId) {
    return checkInRepo.findByBookingId(bookingId);
  }

  private String generateRandomSeat() {
    int row = new Random().nextInt(30) + 1;
    char seat = (char) ('A' + new Random().nextInt(6));
    return row + String.valueOf(seat);
  }
}
