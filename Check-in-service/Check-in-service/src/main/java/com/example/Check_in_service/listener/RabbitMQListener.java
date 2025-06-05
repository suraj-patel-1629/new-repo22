package com.example.Check_in_service.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.example.Check_in_service.config.RabbitMQConfig;
import com.example.Check_in_service.model.BookingEvent;
import com.example.Check_in_service.model.CheckIn;
import com.example.Check_in_service.repo.CheckInRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMQListener {

  private final CheckInRepository checkInRepository;

  
  @RabbitListener(queues = {"${rabbitmq.queue.name}"})
  public void consumeBookingEvent(BookingEvent event) {
    CheckIn checkIn = new CheckIn();
    checkIn.setBookingId(event.getBookingId());
    checkIn.setUserEmail(event.getUserEmail());
    checkIn.setFlightNumber(event.getFlightNumber());
    checkIn.setFlightId(event.getFlightId());
    checkIn.setSeatsBooked(event.getSeatsBooked());
    checkIn.setPrice(event.getPrice());
    checkIn.setStatus(event.getStatus());
    checkIn.setCheckedIn(false);
    checkIn.setCheckInTime(null);
    checkIn.setSeatNumber(null);
}
}