export default function Footer() {
  return (
    <footer style={{ borderTop: "1px solid #ddd", padding: "12px 16px" }}>
      <div style={{ maxWidth: 960, margin: "0 auto", opacity: 0.7 }}>
        © {new Date().getFullYear()} SRRP
      </div>
    </footer>
  );
}