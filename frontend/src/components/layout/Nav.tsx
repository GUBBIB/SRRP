import { NavLink } from "react-router-dom";

const linkStyle = ({ isActive }: { isActive: boolean }) => ({
  padding: "8px 10px",
  borderRadius: 8,
  textDecoration: "none",
  color: "inherit",
  background: isActive ? "rgba(0,0,0,0.08)" : "transparent",
});

export default function Nav() {
  return (
    <nav style={{ borderBottom: "1px solid #eee", padding: "10px 16px" }}>
      <div style={{ maxWidth: 960, margin: "0 auto", display: "flex", gap: 8, flexWrap: "wrap" }}>
        <NavLink to="/" style={linkStyle} end>Home</NavLink>
        <NavLink to="/login" style={linkStyle}>Login</NavLink>
        <NavLink to="/board" style={linkStyle}>Board</NavLink>
        <NavLink to="/board/write" style={linkStyle}>Write</NavLink>
      </div>
    </nav>
  );
}
