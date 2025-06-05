package com.example.Check_in_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Check_in_service.model.CheckIn;
import com.example.Check_in_service.service.CheckInService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/checkin")
@RequiredArgsConstructor
public class CheckInController {

  private final CheckInService checkInService;

  @PostMapping("/{bookingId}")
  public ResponseEntity<CheckIn> checkIn(@PathVariable Long bookingId) {
    return ResponseEntity.ok(checkInService.doCheckIn(bookingId));
  }

  @GetMapping("/{bookingId}")
  public ResponseEntity<CheckIn> getStatus(@PathVariable Long bookingId) {
    return checkInService.getCheckInStatus(bookingId)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }
}
