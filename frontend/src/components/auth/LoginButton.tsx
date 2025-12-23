import React from 'react';
import "./LoginButton.css";

export default function LoginButton() {
  const handleClick = () => {
    alert("여기서 로그인 페이지로 이동하거나 모달을 띄우면 됩니다.");
  };

  return (
    <div id="LoginButton">
      <button className="loginbutton-button" onClick={handleClick}>
        로그인
      </button>
    </div>
  );
}
