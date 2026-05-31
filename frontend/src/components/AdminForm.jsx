import { useState } from "react";

export default function AdminForm({ onCreate, onError }) {
  const [form, setForm] = useState({
    fullName: "",
    phone: "",
    password: "",
  });

  function change(field, value) {
    setForm({
      ...form,
      [field]: value,
    });
  }

  function validate() {
    if (!form.fullName.trim()) {
      onError(
        "Ошибка заполнения",
        "Введите ФИО администратора."
      );
      return false;
    }

    if (!form.phone.trim()) {
      onError(
        "Ошибка заполнения",
        "Введите логин администратора."
      );
      return false;
    }

    if (!form.password.trim()) {
      onError(
        "Ошибка заполнения",
        "Введите пароль администратора."
      );
      return false;
    }

    if (form.password.length < 4) {
      onError(
        "Ошибка заполнения",
        "Пароль должен содержать минимум 4 символа."
      );
      return false;
    }

    return true;
  }

  async function submit(e) {
    e.preventDefault();

    if (!validate()) {
      return;
    }

    await onCreate({
      fullName: form.fullName.trim(),
      phone: form.phone.trim(),
      password: form.password.trim(),
    });

    setForm({
      fullName: "",
      phone: "",
      password: "",
    });
  }

  return (
    <form onSubmit={submit}>
      <h2>Добавить администратора</h2>

      <input
        placeholder="ФИО"
        value={form.fullName}
        onChange={(e) => change("fullName", e.target.value)}
      />

      <input
        placeholder="Логин"
        value={form.phone}
        onChange={(e) => change("phone", e.target.value)}
      />

      <input
        type="password"
        placeholder="Пароль"
        value={form.password}
        onChange={(e) => change("password", e.target.value)}
      />

      <button type="submit">
        Добавить администратора
      </button>
    </form>
  );
}