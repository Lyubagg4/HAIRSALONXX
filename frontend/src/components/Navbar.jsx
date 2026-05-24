import { useNavigate } from "react-router-dom";

export default function Navbar() {
  const navigate = useNavigate();
  const role = localStorage.getItem("role");

  function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    navigate("/login");
  }

  return (
    <header className="navbar">
      <div className="navbar-brand">
        <span className="navbar-logo">✂</span>
        <span>Beauty Salon</span>
      </div>

      <div className="navbar-actions">
        <span className="role-badge">
          {role === "ADMIN" ? "Администратор" : "Руководитель"}
        </span>

        <button className="logout-button" onClick={logout}>
          Выйти
        </button>
      </div>
    </header>
  );
}