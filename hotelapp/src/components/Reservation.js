import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const API_BASE_URL = window.appConfig?.API_BASE_URL || "http://localhost:8080";

const Reservation = ({ selectedRoom }) => {
  const [userId, setUserId] = useState('');
  const [userName, setUserName] = useState('');
  const [userEmail, setUserEmail] = useState('');
  const [userMobile, setUserMobile] = useState('');
  const [responseMessage, setResponseMessage] = useState('');
  
  const navigate = useNavigate();

  if (!selectedRoom) {
    return <p>No room selected. Please go back and select a room.</p>;
  }

  const handleReservation = async () => {
    const reservationData = {
      userId,
      userName,
      userEmail,
      userMobile,
      roomNumber: selectedRoom.number,
      checkinDate: "2025-03-10", // Replace with actual check-in date
      checkoutDate: "2025-03-14" // Replace with actual check-out date
    };

    try {
      const response = await axios.post(`${API_BASE_URL}/reservations/create`, reservationData, {
        headers: { 'Content-Type': 'application/json' }
      });
      
      setResponseMessage(`Reservation Successful: ${response.data.message}`);
    } catch (error) {
      console.error('Error making reservation:', error);
      setResponseMessage('Failed to make a reservation. Please try again.');
    }
  };

  return (
    <div className="reservation-container">
      <h2>Room Reservation</h2>

      {/* Response Message Display */}
      {responseMessage && <p className="response-message">{responseMessage}</p>}

      {/* Read-Only Room Details */}
      <div className="room-details">
        <p><strong>Room Number:</strong> {selectedRoom.number}</p>
        <p><strong>Room Type:</strong> {selectedRoom.roomType}</p>
        <p><strong>Guest Capacity:</strong> {selectedRoom.guestCapacity}</p>
        <p><strong>Price:</strong> ${selectedRoom.price}</p>
      </div>

      {/* Reservation Form */}
      <div className="reservation-form">
        <label>Full Name:</label>
        <input type="text" value={userName} onChange={(e) => setUserName(e.target.value)} />

        <label>Email:</label>
        <input type="email" value={userEmail} onChange={(e) => setUserEmail(e.target.value)} />

        <label>Phone:</label>
        <input type="tel" value={userMobile} onChange={(e) => setUserMobile(e.target.value)} />

        <label>Customer ID:</label>
        <input type="text" value={userId} onChange={(e) => setUserId(e.target.value)} />

        <button onClick={handleReservation}>Reserve</button>
        <button onClick={() => navigate('/')}>Back</button>
      </div>
    </div>
  );
};

export default Reservation;
