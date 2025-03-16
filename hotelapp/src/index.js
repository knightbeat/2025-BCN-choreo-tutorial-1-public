// src/index.js

import React from 'react';
import ReactDOM from 'react-dom/client'; // Use `react-dom/client` instead of `react-dom`
import App from './App';
import './index.css';

const root = ReactDOM.createRoot(document.getElementById('root')); // Create the root element
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
