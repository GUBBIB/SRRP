import React from 'react';
import "./Header.css";

export default function Header() {
  return (
    <div id="Header">
      <div className="header-container">
        <h1 className="header-title">My Vite React App</h1>
        <div className="header-nav">
          <a href="/">Home</a>
          {/* 나중에 /login, /board 추가 가능 */}
          테스트
        </div>
      </div>
    </div>
  );
}
