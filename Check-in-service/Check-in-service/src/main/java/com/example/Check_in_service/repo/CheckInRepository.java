package com.example.Check_in_service.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Check_in_service.model.CheckIn;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
  Optional<CheckIn> findByBookingId(Long bookingId);
}
