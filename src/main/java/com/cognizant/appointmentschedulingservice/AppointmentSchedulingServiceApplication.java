package com.cognizant.appointmentschedulingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

@EnableBinding(Source.class)
@EnableDiscoveryClient
@SpringBootApplication
public class AppointmentSchedulingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppointmentSchedulingServiceApplication.class, args);
	}

}
