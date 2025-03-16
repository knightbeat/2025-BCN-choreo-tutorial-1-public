// src/App.js

import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Import Routes
import RoomBooking from './components/RoomBooking';
import Reservation from './components/Reservation';
import './App.css';

const App = () => {
  const [selectedRoom, setSelectedRoom] = useState(null);

  return (
    <Router>
      <Routes> {/* Replace Switch with Routes */}
        <Route
          path="/"
          element={<RoomBooking setSelectedRoom={setSelectedRoom} />} // Use element prop instead of render
        />
        <Route
          path="/reservation"
          element={<Reservation selectedRoom={selectedRoom} setSelectedRoom={setSelectedRoom} />} // Use element prop instead of render
        />
      </Routes>
    </Router>
  );
};

export default App;
