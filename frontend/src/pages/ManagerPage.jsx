import { useEffect, useMemo, useState } from "react";

import Navbar from "../components/Navbar";
import Notification from "../components/Notification";
import AdminForm from "../components/AdminForm";

import { createAdmin } from "../api/adminApi";
import { getMasters } from "../api/masterApi";

import {
  getClientsByDate,
  getGenderStats,
  getMasterIncome,
  getPopularService,
  getRegularClientsCount,
  getTopMaster,
} from "../api/reportApi";

import "../styles/manager.css";

export default function ManagerPage() {
  const [notification, setNotification] = useState(null);

  const [date, setDate] = useState(new Date().toISOString().slice(0, 10));
  const [selectedMasterId, setSelectedMasterId] = useState("");

  const [masters, setMasters] = useState([]);
  const [clientsByDate, setClientsByDate] = useState([]);
  const [masterIncome, setMasterIncome] = useState(null);

  const [popularService, setPopularService] = useState(null);
  const [genderStats, setGenderStats] = useState(null);
  const [regularClients, setRegularClients] = useState(null);
  const [topMaster, setTopMaster] = useState(null);

  function showNotification(type, title, message) {
    setNotification({ type, title, message });
  }

  async function loadDashboard() {
    try {
      const [
        loadedMasters,
        popularServiceReport,
        genderStatsReport,
        regularClientsReport,
        topMasterReport,
      ] = await Promise.all([
        getMasters(),
        getPopularService(),
        getGenderStats(),
        getRegularClientsCount(),
        getTopMaster(),
      ]);

      setMasters(loadedMasters);
      setPopularService(popularServiceReport);
      setGenderStats(genderStatsReport);
      setRegularClients(regularClientsReport);
      setTopMaster(topMasterReport);
    } catch (error) {
      showNotification(
        "error",
        "Ошибка загрузки",
        "Не удалось загрузить данные панели руководителя."
      );
    }
  }

  async function handleLoadClientsByDate() {
    try {
      const result = await getClientsByDate(date);
      setClientsByDate(result);

      showNotification(
        "success",
        "Отчёт сформирован",
        "Список клиентов за выбранную дату обновлён."
      );
    } catch (error) {
      showNotification(
        "error",
        "Ошибка отчёта",
        "Не удалось получить клиентов за выбранную дату."
      );
    }
  }

  async function handleLoadMasterIncome() {
    if (!selectedMasterId) {
      showNotification(
        "error",
        "Ошибка заполнения",
        "Выберите мастера из списка."
      );
      return;
    }

    try {
      const result = await getMasterIncome(Number(selectedMasterId));
      setMasterIncome(result);

      showNotification(
        "success",
        "Доход рассчитан",
        "Отчёт по доходу выбранного мастера обновлён."
      );
    } catch (error) {
      showNotification(
        "error",
        "Ошибка расчёта",
        "Не удалось рассчитать доход мастера."
      );
    }
  }

  async function handleCreateAdmin(admin) {
    try {
      await createAdmin(admin);

      showNotification(
        "success",
        "Администратор добавлен",
        "Новый администратор успешно создан."
      );
    } catch (error) {
      showNotification(
        "error",
        "Ошибка добавления",
        "Не удалось добавить администратора. Возможно, такой логин или телефон уже существует."
      );
    }
  }

  const selectedMasterName = useMemo(() => {
    const master = masters.find(
      (item) => String(item.id) === String(selectedMasterId)
    );

    if (!master) {
      return "Мастер не выбран";
    }

    return master.fullName;
  }, [masters, selectedMasterId]);

  function exportToCsv() {
    const rows = [
      ["Показатель", "Значение"],
      ["Популярная услуга", popularService?.value || "-"],
      ["Соотношение клиентов по полу", genderStats?.value || "-"],
      ["Количество постоянных клиентов", regularClients?.value || "-"],
      ["Лучший мастер", topMaster?.value || "-"],
      ["Выбранный мастер", selectedMasterName],
      ["Доход мастера", masterIncome?.value || "-"],
      ["Клиентов за дату", clientsByDate.length],
    ];

    const csv = rows
      .map((row) =>
        row
          .map((cell) => `"${String(cell).replaceAll('"', '""')}"`)
          .join(";")
      )
      .join("\n");

    const blob = new Blob(["\uFEFF" + csv], {
      type: "text/csv;charset=utf-8;",
    });

    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "beauty-salon-reports.csv";
    link.click();
    URL.revokeObjectURL(link.href);

    showNotification(
      "success",
      "CSV сформирован",
      "Файл с отчётами успешно выгружен."
    );
  }

  useEffect(() => {
    loadDashboard();
  }, []);

  return (
    <>
      <Navbar />

      <Notification
        notification={notification}
        onClose={() => setNotification(null)}
      />

      <main className="manager-page">
        <section className="manager-panel">
          <div className="manager-header">
            <div>
              <h1>Панель руководителя</h1>
              <p>Аналитика, отчёты и управление администраторами</p>
            </div>

            <button className="export-btn" onClick={exportToCsv}>
              CSV экспорт
            </button>
          </div>

          <section className="manager-section">
            <div className="section-title">
              <h2>Основные показатели</h2>
              <p>Сводная информация по работе салона</p>
            </div>

            <div className="stats-grid">
              <div className="stat-card purple">
                <span>Популярная услуга</span>
                <strong>{popularService?.value || "Нет данных"}</strong>
              </div>

              <div className="stat-card pink">
                <span>Клиенты по полу</span>
                <strong>{genderStats?.value || "Нет данных"}</strong>
              </div>

              <div className="stat-card green">
                <span>Постоянные клиенты</span>
                <strong>{regularClients?.value || "Нет данных"}</strong>
              </div>

              <div className="stat-card orange">
                <span>Лучший мастер</span>
                <strong>{topMaster?.value || "Нет данных"}</strong>
              </div>
            </div>
          </section>

          <section className="manager-section">
            <div className="section-title">
              <h2>Отчёты</h2>
              <p>Формирование отчётов по дате и мастеру</p>
            </div>

            <div className="report-tools">
              <div className="tool-card">
                <h3>Клиенты за дату</h3>

                <div className="tool-row">
                  <input
                    type="date"
                    value={date}
                    onChange={(e) => setDate(e.target.value)}
                  />

                  <button onClick={handleLoadClientsByDate}>
                    Показать
                  </button>
                </div>

                <div className="tool-result">
                  <span>Найдено клиентов</span>
                  <strong>{clientsByDate.length}</strong>
                </div>
              </div>

              <div className="tool-card">
                <h3>Доход мастера</h3>

                <div className="tool-row">
                  <select
                    value={selectedMasterId}
                    onChange={(e) => setSelectedMasterId(e.target.value)}
                  >
                    <option value="">Выберите мастера</option>

                    {masters.map((master) => (
                      <option key={master.id} value={master.id}>
                        {master.fullName}
                      </option>
                    ))}
                  </select>

                  <button onClick={handleLoadMasterIncome}>
                    Рассчитать
                  </button>
                </div>

                <div className="tool-result">
                  <span>{selectedMasterName}</span>
                  <strong>{masterIncome?.value || "—"}</strong>
                </div>
              </div>
            </div>
          </section>

          <section className="manager-section">
            <div className="section-title">
              <h2>Администраторы</h2>
              <p>Создание новой учётной записи администратора</p>
            </div>

            <div className="admin-create-card">
              <AdminForm
                onCreate={handleCreateAdmin}
                onError={(title, message) =>
                  showNotification("error", title, message)
                }
              />
            </div>
          </section>
        </section>

        <footer className="app-footer">
          Beauty Salon © 2026 — аналитическая панель руководителя
        </footer>
      </main>
    </>
  );
}