import { useState } from "react";

export default function MasterForm({ onCreate, onError }) {
  const [form, setForm] = useState({
    fullName: "",
    phone: "",
    gender: "FEMALE",
    specialization: "FEMALE_HALL",
    qualification: "HAIRDRESSER",
  });

  function change(field, value) {
    setForm({ ...form, [field]: value });
  }

  function validate() {
    const phoneRegex = /^(\+7|8)\d{10}$/;

    if (!form.fullName.trim()) {
      onError("Ошибка заполнения", "Введите ФИО мастера.");
      return false;
    }

    if (!phoneRegex.test(form.phone.trim())) {
      onError(
        "Ошибка заполнения",
        "Введите корректный номер телефона. Например: 89001234567."
      );
      return false;
    }

    if (!form.qualification) {
      onError("Ошибка заполнения", "Выберите квалификацию мастера.");
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
      specialization: "FEMALE_HALL",
      qualification: "HAIRDRESSER",
    });
  }

  return (
    <form onSubmit={submit}>
      <h2>Добавить мастера</h2>

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
        value={form.specialization}
        onChange={(e) => change("specialization", e.target.value)}
      >
        <option value="FEMALE_HALL">Женский зал</option>
        <option value="MALE_HALL">Мужской зал</option>
      </select>

      <select
        value={form.qualification}
        onChange={(e) => change("qualification", e.target.value)}
      >
        <option value="HAIRDRESSER">Парикмахер</option>
        <option value="BARBER">Барбер</option>
        <option value="NAIL_MASTER">Ногтевой сервис</option>
        <option value="COLORIST">Колорист</option>
        <option value="STYLIST">Стилист</option>
      </select>

      <button type="submit">Добавить</button>
    </form>
  );
}