package com.hotel.reservations.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.reservations.model.Room;
import com.hotel.reservations.service.RoomService;

import java.util.List;

@RestController
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/available-rooms")
    public List<Room> getAvailableRooms(
            @RequestParam int guestCapacity,
            @RequestParam String givenStartDate,
            @RequestParam String givenEndDate) {
        return roomService.findAvailableRooms(guestCapacity, givenStartDate, givenEndDate);
    }
}


