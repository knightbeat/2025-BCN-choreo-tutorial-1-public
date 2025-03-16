package com.hotel.reservations.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hotel.reservations.model.Room;

import java.util.List;

@Repository
public class RoomDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoomDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Room> findAvailableRooms(int guestCapacity, String givenStartDate, String givenEndDate) {
        String sql = "SELECT r.number AS roomNumber, rt.name AS roomType, rt.guest_capacity, rt.price " +
                     "FROM room r " +
                     "JOIN room_type rt ON r.type_id = rt.id " +
                     "WHERE rt.guest_capacity >= ? " +
                     "AND r.number NOT IN ( " +
                     "    SELECT res.room_number FROM reservation res " +
                     "    WHERE res.checkin_date < ? AND res.checkout_date > ? " +
                     ") " +
                     "ORDER BY r.number";

        return jdbcTemplate.query(sql, new Object[]{guestCapacity, givenEndDate, givenStartDate}, (rs, rowNum) -> {
            Room room = new Room();
            room.setNumber(rs.getInt("roomNumber"));
            room.setRoomType(rs.getString("roomType"));
            room.setGuestCapacity(rs.getInt("guest_capacity"));
            room.setPrice(rs.getBigDecimal("price"));
            return room;
        });
    }
}

