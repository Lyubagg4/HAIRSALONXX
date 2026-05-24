import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../api/authApi";
import "../styles/login.css";

export default function LoginPage() {
  const [phone, setPhone] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();

    try {
      const data = await login(phone, password);

      localStorage.setItem("token", data.token);
      localStorage.setItem("role", data.role);

      if (data.role === "ADMIN") {
        navigate("/admin");
      } else {
        navigate("/manager");
      }
    } catch (e) {
      alert("Ошибка авторизации");
    }
  }

  return (
    <main className="login-page">
      <div className="login-wrapper">
        <div className="login-side">
          <div className="logo-circle">✂</div>

          <h1>Beauty Salon</h1>

          <p>
            Информационная система
            <br />
            салона красоты
          </p>
        </div>

        <form className="login-card" onSubmit={handleSubmit}>
          <h2>Вход</h2>

          <input
            value={phone}
            onChange={(e) => setPhone(e.target.value)}
            placeholder="Логин"
          />

          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="Пароль"
          />

          <button type="submit">Войти</button>
        </form>
      </div>
    </main>
  );
}