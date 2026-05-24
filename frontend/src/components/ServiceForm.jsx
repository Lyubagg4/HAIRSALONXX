import { useState } from "react";

export default function ServiceForm({ onCreate, onError }) {
  const [form, setForm] = useState({
    name: "",
    type: "HAIRCUT",
    hallType: "FEMALE_HALL",
    price: 1000,
  });

  function change(field, value) {
    setForm({ ...form, [field]: value });
  }

  function validate() {
    if (!form.name.trim()) {
      onError("Ошибка заполнения", "Введите название услуги.");
      return false;
    }

    if (!form.price || form.price <= 0) {
      onError("Ошибка заполнения", "Цена услуги должна быть больше 0.");
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
      name: form.name.trim(),
      price: Number(form.price),
    });

    setForm({
      name: "",
      type: "HAIRCUT",
      hallType: "FEMALE_HALL",
      price: 1000,
    });
  }

  return (
    <form onSubmit={submit}>
      <h2>Добавить услугу</h2>

      <input
        placeholder="Название"
        value={form.name}
        onChange={(e) => change("name", e.target.value)}
      />

      <select
        value={form.type}
        onChange={(e) => change("type", e.target.value)}
      >
        <option value="HAIRCUT">Стрижка</option>
        <option value="COLORING">Окрашивание</option>
        <option value="STYLING">Укладка</option>
      </select>

      <select
        value={form.hallType}
        onChange={(e) => change("hallType", e.target.value)}
      >
        <option value="FEMALE_HALL">Женский зал</option>
        <option value="MALE_HALL">Мужской зал</option>
      </select>

      <input
        type="number"
        placeholder="Цена"
        value={form.price}
        onChange={(e) => change("price", Number(e.target.value))}
      />

      <button type="submit">Добавить</button>
    </form>
  );
}