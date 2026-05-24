import { useState } from "react";

export default function VisitForm({ clients, masters, services, onCreate }) {
  const [form, setForm] = useState({
    clientId: "",
    masterId: "",
    visitDate: new Date().toISOString().slice(0, 10),
    serviceIds: [],
  });

  function change(field, value) {
    setForm({ ...form, [field]: value });
  }

  function toggleService(serviceId) {
    const exists = form.serviceIds.includes(serviceId);

    setForm({
      ...form,
      serviceIds: exists
        ? form.serviceIds.filter((id) => id !== serviceId)
        : [...form.serviceIds, serviceId],
    });
  }

  async function submit(e) {
    e.preventDefault();

    await onCreate({
      clientId: Number(form.clientId),
      masterId: Number(form.masterId),
      visitDate: form.visitDate,
      serviceIds: form.serviceIds,
    });

    setForm({
      clientId: "",
      masterId: "",
      visitDate: new Date().toISOString().slice(0, 10),
      serviceIds: [],
    });
  }

  return (
    <form onSubmit={submit}>
      <h2>Зафиксировать обслуживание</h2>

      <select
        value={form.clientId}
        onChange={(e) => change("clientId", e.target.value)}
        required
      >
        <option value="">Выберите клиента</option>
        {clients.map((client) => (
          <option key={client.id} value={client.id}>
            {client.fullName}
          </option>
        ))}
      </select>

      <select
        value={form.masterId}
        onChange={(e) => change("masterId", e.target.value)}
        required
      >
        <option value="">Выберите мастера</option>
        {masters.map((master) => (
          <option key={master.id} value={master.id}>
            {master.fullName}
          </option>
        ))}
      </select>

      <input
        type="date"
        value={form.visitDate}
        onChange={(e) => change("visitDate", e.target.value)}
      />

      <div>
        <p>Услуги:</p>

        {services.map((service) => (
          <label key={service.id} style={{ display: "block" }}>
            <input
              type="checkbox"
              checked={form.serviceIds.includes(service.id)}
              onChange={() => toggleService(service.id)}
            />
            {service.name} — {service.price}
          </label>
        ))}
      </div>

      <button type="submit">Сохранить визит</button>
    </form>
  );
}