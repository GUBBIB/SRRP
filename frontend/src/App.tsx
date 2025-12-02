import React from 'react';
import './App.css'
import Header from './Component/Header/Header';
import Home from './Component/Home/Home';
import Footer from './Component/Footer/Footer';

export default function App() {
  return (
    <div id="App">
      <div className="app-container">
        <Header />
        <Home />
        <Footer />
      </div>
    </div>
  );
}