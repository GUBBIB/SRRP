import React from 'react';
import { Routes, Route } from 'react-router-dom';
import App from './App';

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/" element={<App />} />
      {/* Add more routes here as needed */}
      {/* <Route path="/login" element={<LoginPage />} /> */}
    </Routes>
  );
}
