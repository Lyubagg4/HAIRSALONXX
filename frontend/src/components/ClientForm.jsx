import { useState } from "react";

export default function ClientForm({ clients = [], onCreate, onError }) {
  const [form, setForm] = useState({
    fullName: "",
    phone: "",
    gender: "FEMALE",
    category: "REGULAR",
    discount: 10,
  });

  function change(field, value) {
    setForm({ ...form, [field]: value });
  }

  function validate() {
    const phone = form.phone.trim();
    const phoneRegex = /^(\+7|8)\d{10}$/;

    if (!form.fullName.trim()) {
      onError("Ошибка заполнения", "Введите ФИО клиента.");
      return false;
    }

    if (!phoneRegex.test(phone)) {
      onError(
        "Ошибка заполнения",
        "Введите корректный номер телефона. Например: 89001234567."
      );
      return false;
    }

    const exists = clients.some(
      (client) => String(client.phone).trim() === phone
    );

    if (exists) {
      onError("Ошибка добавления", "Клиент с таким номером телефона уже существует.");
      return false;
    }

    if (form.discount < 0 || form.discount > 100) {
      onError("Ошибка заполнения", "Скидка должна быть от 0 до 100.");
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
      ...form,
      fullName: form.fullName.trim(),
      phone: form.phone.trim(),
    });

    setForm({
      fullName: "",
      phone: "",
      gender: "FEMALE",
      category: "REGULAR",
      discount: 10,
    });
  }

  return (
    <form onSubmit={submit}>
      <h2>Добавить клиента</h2>

      <input
        placeholder="ФИО"
        value={form.fullName}
        onChange={(e) => change("fullName", e.target.value)}
      />

      <input
        placeholder="Телефон, например 89001234567"
        value={form.phone}
        onChange={(e) => change("phone", e.target.value)}
      />

      <select
        value={form.gender}
        onChange={(e) => change("gender", e.target.value)}
      >
        <option value="FEMALE">Женский</option>
        <option value="MALE">Мужской</option>
      </select>

      <select
        value={form.category}
        onChange={(e) => change("category", e.target.value)}
      >
        <option value="REGULAR">Постоянный</option>
        <option value="RANDOM">Случайный</option>
      </select>

      <input
        type="number"
        placeholder="Скидка"
        value={form.discount}
        onChange={(e) => change("discount", Number(e.target.value))}
      />

      <button type="submit">Добавить</button>
    </form>
  );
}