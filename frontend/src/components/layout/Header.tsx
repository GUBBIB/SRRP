import { Link } from "react-router-dom";

export default function Header() {
  return (
    <header style={{ borderBottom: "1px solid #ddd", padding: "12px 16px" }}>
      <div style={{ maxWidth: 960, margin: "0 auto", display: "flex", gap: 12, alignItems: "center" }}>
        <Link to="/" style={{ fontWeight: 700, textDecoration: "none", color: "inherit" }}>
          SRRP Club
        </Link>
        <span style={{ opacity: 0.7 }}>동아리 홈페이지 (SPA)</span>
      </div>
    </header>
  );
}
