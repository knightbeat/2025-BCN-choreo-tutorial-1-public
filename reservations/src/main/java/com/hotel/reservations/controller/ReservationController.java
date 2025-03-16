package com.hotel.reservations.controller;

import com.hotel.reservations.model.Reservation;
import com.hotel.reservations.model.User;
import com.hotel.reservations.service.KafkaProducerService;
import com.hotel.reservations.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public ReservationController(ReservationService reservationService, KafkaProducerService kafkaProducerService) {
        this.reservationService = reservationService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createReservation(@RequestBody ReservationRequest request) {
        User user = new User(request.getUserId(), request.getUserName(), request.getUserEmail(), request.getUserMobile());
        Reservation reservation = new Reservation(0, request.getRoomNumber(), request.getCheckinDate(), request.getCheckoutDate(), request.getUserId());

        //reservationService.createReservation(user, reservation);

         // Send reservation notification message to Kafka
         String reservationMessage = String.format(
            "{\"userId\":\"%s\", \"userName\":\"%s\", \"userEmail\":\"%s\", \"userMobile\":\"%s\", \"roomNumber\":%d, \"checkinDate\":\"%s\", \"checkoutDate\":\"%s\"}",
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getMobileNumber(),
            reservation.getRoomNumber(),
            reservation.getCheckinDate(),
            reservation.getCheckoutDate()
        );

        kafkaProducerService.sendMessage(reservationMessage);

        // Creating a JSON response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Reservation successfully created!");
        response.put("code", 201);

        return ResponseEntity.status(201).body(response);
    }
}
