package com.hotel.reservations.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.reservations.dto.ReservationDao;
import com.hotel.reservations.dto.UserDao;
import com.hotel.reservations.model.Reservation;
import com.hotel.reservations.model.User;

@Service
public class ReservationService {

    private final UserDao userDao;
    private final ReservationDao reservationDao;

    @Autowired
    public ReservationService(UserDao userDao, ReservationDao reservationDao) {
        this.userDao = userDao;
        this.reservationDao = reservationDao;
    }

    public void createReservation(User user, Reservation reservation) {
        // Insert user if not already in the database
        userDao.insertUser(user);
        
        // Insert reservation
        reservationDao.insertReservation(reservation);
    }
}

