// src/components/RoomBooking.js

import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom'; // Import useNavigate instead of useHistory

const API_BASE_URL = window.appConfig?.API_BASE_URL || "http://localhost:8080";

const RoomBooking = ({ setSelectedRoom }) => {
  const [checkinDate, setCheckinDate] = useState('');
  const [checkoutDate, setCheckoutDate] = useState('');
  const [numGuests, setNumGuests] = useState(1);
  const [rooms, setRooms] = useState([]);

  // Handle form submission to call the API
  const handleSubmit = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/available-rooms`, {
        params: {
          guestCapacity: numGuests,
          givenStartDate: checkinDate,
          givenEndDate: checkoutDate,
        }
      });

      setRooms(response.data);  // Store the response data in state
    } catch (error) {
      console.error('Error fetching available rooms:', error);
    }
  };

  const navigate = useNavigate(); // Use useNavigate instead of useHistory

  const handleReserve = (room) => {
    setSelectedRoom(room);  // Save selected room details to state
    navigate('/reservation');  // Navigate to reservation view
  };

  return (
    <div className="App">
      <div>
        <h1>Room Booking</h1>
        <div className="form-container">
          <div>
            <label>Check-in Date:</label>
            <input
              type="date"
              value={checkinDate}
              onChange={(e) => setCheckinDate(e.target.value)}
            />
          </div>

          <div>
            <label>Check-out Date:</label>
            <input
              type="date"
              value={checkoutDate}
              onChange={(e) => setCheckoutDate(e.target.value)}
            />
          </div>

          <div>
            <label>Number of Guests (1-4):</label>
            <input
              type="number"
              min="1"
              max="4"
              value={numGuests}
              onChange={(e) => setNumGuests(e.target.value)}
            />
          </div>
        </div>

        <div>
          <button onClick={handleSubmit}>Get Available Rooms</button>
        </div>
      </div>

      <div className="room-list">
        {rooms.length > 0 && (
          <>
            <h2>Available Rooms:</h2>
            <table>
              <thead>
                <tr>
                  <th>Room Number</th>
                  <th>Room Type</th>
                  <th>Guest Capacity</th>
                  <th>Price</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {rooms.map((room) => (
                  <tr key={room.number}>
                    <td>{room.number}</td>
                    <td>{room.roomType}</td>
                    <td>{room.guestCapacity}</td>
                    <td>${room.price}</td>
                    <td>
                      <button onClick={() => handleReserve(room)}>Reserve</button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </>
        )}
      </div>
    </div>
  );
};

export default RoomBooking;
