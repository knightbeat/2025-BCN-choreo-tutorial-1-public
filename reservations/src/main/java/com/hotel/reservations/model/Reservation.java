package com.hotel.reservations.model;

import java.time.LocalDate;

public class Reservation {
    private int id;
    private int roomNumber;
    private LocalDate checkinDate;
    private LocalDate checkoutDate;
    private String userId;

    // Constructors
    public Reservation() {}

    public Reservation(int id, int roomNumber, LocalDate checkinDate, LocalDate checkoutDate, String userId) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.userId = userId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getRoomNumber() { return roomNumber; }
    public void setRoomNumber(int roomNumber) { this.roomNumber = roomNumber; }

    public LocalDate getCheckinDate() { return checkinDate; }
    public void setCheckinDate(LocalDate checkinDate) { this.checkinDate = checkinDate; }

    public LocalDate getCheckoutDate() { return checkoutDate; }
    public void setCheckoutDate(LocalDate checkoutDate) { this.checkoutDate = checkoutDate; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}

