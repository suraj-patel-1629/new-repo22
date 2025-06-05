package com.example.payment_servcie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentServcieApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServcieApplication.class, args);
	}

}
