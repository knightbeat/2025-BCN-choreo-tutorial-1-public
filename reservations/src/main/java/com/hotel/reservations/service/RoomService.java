package com.hotel.reservations.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.reservations.dto.RoomDao;
import com.hotel.reservations.model.Room;

import java.util.List;

@Service
public class RoomService {

    private final RoomDao roomDao;

    @Autowired
    public RoomService(RoomDao roomDao) {
        this.roomDao = roomDao;
    }

    public List<Room> findAvailableRooms(int guestCapacity, String givenStartDate, String givenEndDate) {
        return roomDao.findAvailableRooms(guestCapacity, givenStartDate, givenEndDate);
    }
}


