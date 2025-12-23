import React from 'react';
import "./HomePage.css";
import LoginButton from "../../components/auth/LoginButton";

export default function HomePage() {
  return (
    <div id="Home">
      <div className="home-container">
        <h2 className="home-title">환영합니다 👋</h2>
        <p className="home-desc">
          이 사이트는 React + Vite 기반으로 제작된 프로젝트입니다.
          <br /> 아래 버튼을 눌러 로그인해보세요.
        </p>

        <div className="home-card">
          <h3 className="home-card-title">시작해볼까요?</h3>
          <p className="home-card-text">
            로그인하면 더 많은 기능을 이용할 수 있어요.
          </p>
          <LoginButton />
        </div>
      </div>
    </div>
  );
}
