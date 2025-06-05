package com.example.payment_servcie.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.payment_servcie.model.Payment;

import com.example.payment_servcie.repo.PaymentRepo;

@Service
public class PaymentService {
  @Autowired
  private PaymentRepo paymentRepo;

  public Payment getPaymentDetail(Long id) {
    return paymentRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
  }

  public List<Payment> getAllPaymentDetail() {
    List<Payment> details = new ArrayList<>();
    details = paymentRepo.findAll();
    return details;
  }

}
