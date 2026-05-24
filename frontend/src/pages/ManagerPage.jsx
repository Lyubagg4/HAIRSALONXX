import { useEffect, useState } from "react";

import Navbar from "../components/Navbar";
import ReportCard from "../components/ReportCard";

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
  const [date, setDate] = useState(new Date().toISOString().slice(0, 10));
  const [masterId, setMasterId] = useState("");

  const [clientsByDate, setClientsByDate] = useState([]);
  const [masterIncome, setMasterIncome] = useState(null);
  const [popularService, setPopularService] = useState(null);
  const [genderStats, setGenderStats] = useState(null);
  const [regularClients, setRegularClients] = useState(null);
  const [topMaster, setTopMaster] = useState(null);

  async function loadMainReports() {
    setPopularService(await getPopularService());
    setGenderStats(await getGenderStats());
    setRegularClients(await getRegularClientsCount());
    setTopMaster(await getTopMaster());
  }

  async function handleLoadClientsByDate() {
    setClientsByDate(await getClientsByDate(date));
  }

  async function handleLoadMasterIncome() {
    if (!masterId) {
      alert("Введите ID мастера");
      return;
    }

    setMasterIncome(await getMasterIncome(Number(masterId)));
  }

  function exportToCsv() {
    const rows = [
      ["Отчет", "Значение"],
      [popularService?.title, popularService?.value],
      [genderStats?.title, genderStats?.value],
      [regularClients?.title, regularClients?.value],
      [topMaster?.title, topMaster?.value],
      [masterIncome?.title, masterIncome?.value],
    ];

    const csv = rows
      .filter((row) => row[0])
      .map((row) => row.join(";"))
      .join("\n");

    const blob = new Blob([csv], {
      type: "text/csv;charset=utf-8;",
    });

    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "beauty-salon-reports.csv";
    link.click();
  }

  useEffect(() => {
    loadMainReports();
  }, []);

  return (
    <>
      <Navbar />

      <main className="manager-page">
        <section className="manager-panel">
          <div className="manager-header">
            <div>
              <h1>Панель руководителя</h1>
              <p>Аналитика и статистика салона красоты</p>
            </div>

            <button className="export-btn" onClick={exportToCsv}>
              CSV экспорт
            </button>
          </div>

          <div className="manager-toolbar">
            <div className="toolbar-card">
              <h3>Клиенты за дату</h3>

              <div className="toolbar-row">
                <input
                  type="date"
                  value={date}
                  onChange={(e) => setDate(e.target.value)}
                />

                <button onClick={handleLoadClientsByDate}>
                  Показать
                </button>
              </div>
            </div>

            <div className="toolbar-card">
              <h3>Доход мастера</h3>

              <div className="toolbar-row">
                <input
                  type="number"
                  placeholder="ID мастера"
                  value={masterId}
                  onChange={(e) => setMasterId(e.target.value)}
                />

                <button onClick={handleLoadMasterIncome}>
                  Рассчитать
                </button>
              </div>
            </div>
          </div>

          <div className="report-grid">
            <ReportCard color="purple" report={popularService} />
            <ReportCard color="pink" report={genderStats} />
            <ReportCard color="green" report={regularClients} />
            <ReportCard color="orange" report={topMaster} />
            <ReportCard
              color="cyan"
              report={
                masterIncome || {
                  title: "Доход мастера",
                  value: "Введите ID",
                }
              }
            />

            <div className="report-card blue">
              <h3>Клиенты за дату</h3>
              <p>{clientsByDate.length}</p>

              <ul className="clients-list">
                {clientsByDate.map((item, index) => (
                  <li key={index}>{item.value}</li>
                ))}
              </ul>
            </div>
          </div>
        </section>

        <footer className="app-footer">
          Beauty Salon © 2026 — аналитическая панель руководителя
        </footer>
      </main>
    </>
  );
}