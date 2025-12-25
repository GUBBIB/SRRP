import { Outlet } from "react-router-dom";
import Header from "./Header";
import Nav from "./Nav";
import Footer from "./Footer";

export default function RootLayout() {
  return (
    <div style={{ minHeight: "100vh", display: "flex", flexDirection: "column" }}>
      <Header />
      <Nav />

      <main style={{ flex: 1, padding: "16px", maxWidth: 960, margin: "0 auto", width: "100%" }}>
        <Outlet />
      </main>

      <Footer />
    </div>
  );
}
