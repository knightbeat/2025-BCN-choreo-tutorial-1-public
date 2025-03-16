package com.hotel.reservations.dto;

import com.hotel.reservations.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertReservation(Reservation reservation) {
        String sql = "INSERT INTO reservation (room_number, checkin_date, checkout_date, user_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, reservation.getRoomNumber(), reservation.getCheckinDate(), reservation.getCheckoutDate(), reservation.getUserId());
    }
}

