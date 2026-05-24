import { useEffect, useState } from "react";

import Navbar from "../components/Navbar";
import Notification from "../components/Notification";

import ClientForm from "../components/ClientForm";
import ClientTable from "../components/ClientTable";
import { createClient, deleteClient, getClients } from "../api/clientApi";

import MasterForm from "../components/MasterForm";
import MasterTable from "../components/MasterTable";
import { createMaster, deleteMaster, getMasters } from "../api/masterApi";

import ServiceForm from "../components/ServiceForm";
import ServiceTable from "../components/ServiceTable";
import { createService, deleteService, getServices } from "../api/serviceApi";

import VisitForm from "../components/VisitForm";
import VisitTable from "../components/VisitTable";
import { createVisit, getVisits } from "../api/visitApi";

import "../styles/admin.css";

export default function AdminPage() {
  const [activeSection, setActiveSection] = useState(null);
  const [notification, setNotification] = useState(null);

  const [clients, setClients] = useState([]);
  const [masters, setMasters] = useState([]);
  const [services, setServices] = useState([]);
  const [visits, setVisits] = useState([]);

  function showNotification(type, title, message) {
    setNotification({ type, title, message });
  }

  function showError(title, message) {
    showNotification("error", title, message);
  }

  function isDuplicatePhoneError(error) {
    const message =
      error.response?.data?.message ||
      error.response?.data ||
      error.message ||
      "";

    return (
      String(message).includes("users_phone_key") ||
      String(message).includes("duplicate key") ||
      String(message).includes("already exists")
    );
  }

  async function loadClients() {
    setClients(await getClients());
  }

  async function loadMasters() {
    setMasters(await getMasters());
  }

  async function loadServices() {
    setServices(await getServices());
  }

  async function loadVisits() {
    setVisits(await getVisits());
  }

  async function handleCreateClient(client) {
    try {
      await createClient(client);
      await loadClients();

      showNotification(
        "success",
        "Клиент добавлен",
        "Данные клиента успешно сохранены."
      );
    } catch (error) {
      if (isDuplicatePhoneError(error)) {
        showError(
          "Ошибка добавления",
          "Пользователь с таким номером телефона уже существует."
        );
      } else {
        showError(
          "Ошибка добавления",
          "Не удалось добавить клиента. Проверьте введённые данные."
        );
      }
    }
  }

  function handleDeleteClient(id) {
    setNotification({
      type: "confirm",
      title: "Удаление клиента",
      message: "Удалить клиента? Это действие нельзя отменить.",
      onConfirm: async () => {
        try {
          await deleteClient(id);
          await loadClients();

          showNotification(
            "success",
            "Клиент удалён",
            "Запись клиента была удалена из системы."
          );
        } catch (error) {
          showError(
            "Ошибка удаления",
            "Не удалось удалить клиента. Возможно, он связан с посещениями."
          );
        }
      },
    });
  }

  async function handleCreateMaster(master) {
    try {
      await createMaster(master);
      await loadMasters();

      showNotification(
        "success",
        "Мастер добавлен",
        "Данные мастера успешно сохранены."
      );
    } catch (error) {
      if (isDuplicatePhoneError(error)) {
        showError(
          "Ошибка добавления",
          "Пользователь с таким номером телефона уже существует."
        );
      } else {
        showError(
          "Ошибка добавления",
          "Не удалось добавить мастера. Проверьте введённые данные."
        );
      }
    }
  }

  function handleDeleteMaster(id) {
    setNotification({
      type: "confirm",
      title: "Удаление мастера",
      message: "Удалить мастера? Это действие нельзя отменить.",
      onConfirm: async () => {
        try {
          await deleteMaster(id);
          await loadMasters();

          showNotification(
            "success",
            "Мастер удалён",
            "Запись мастера была удалена из системы."
          );
        } catch (error) {
          showError(
            "Ошибка удаления",
            "Не удалось удалить мастера. Возможно, он связан с посещениями."
          );
        }
      },
    });
  }

  async function handleCreateService(service) {
    try {
      await createService(service);
      await loadServices();

      showNotification(
        "success",
        "Услуга добавлена",
        "Новая услуга успешно сохранена."
      );
    } catch (error) {
      showError(
        "Ошибка добавления",
        "Не удалось добавить услугу. Проверьте введённые данные."
      );
    }
  }

  function handleDeleteService(id) {
    setNotification({
      type: "confirm",
      title: "Удаление услуги",
      message: "Удалить услугу? Это действие нельзя отменить.",
      onConfirm: async () => {
        try {
          await deleteService(id);
          await loadServices();

          showNotification(
            "success",
            "Услуга удалена",
            "Услуга была удалена из системы."
          );
        } catch (error) {
          showError(
            "Ошибка удаления",
            "Не удалось удалить услугу. Возможно, она связана с посещениями."
          );
        }
      },
    });
  }

  async function handleCreateVisit(visit) {
    try {
      await createVisit(visit);
      await loadVisits();

      showNotification(
        "success",
        "Посещение создано",
        "Запись об обслуживании успешно сохранена."
      );
    } catch (error) {
      showError(
        "Ошибка создания",
        "Не удалось создать посещение. Проверьте выбранные данные."
      );
    }
  }

  useEffect(() => {
    loadClients();
    loadMasters();
    loadServices();
    loadVisits();
  }, []);

  return (
    <>
      <Navbar />

      <Notification
        notification={notification}
        onClose={() => setNotification(null)}
      />

      <main className="admin-page">
        <section className="hero-card">
          <div className="hero-icon">✂</div>

          <h1>Beauty Salon</h1>
          <p>Управление салоном красоты</p>

          <div className="action-grid">
            <button
              className="action-card purple"
              onClick={() => setActiveSection("masters")}
            >
              <span>👩‍🦱</span>
              Добавить мастера
            </button>

            <button
              className="action-card pink"
              onClick={() => setActiveSection("services")}
            >
              <span>💼</span>
              Добавить услугу
            </button>

            <button
              className="action-card blue"
              onClick={() => setActiveSection("clients")}
            >
              <span>👥</span>
              Добавить клиента
            </button>

            <button
              className="action-card green"
              onClick={() => setActiveSection("visits")}
            >
              <span>📅</span>
              Обслуживание
            </button>
          </div>
        </section>

        {activeSection === "clients" && (
          <section className="window-card blue-bg">
            <button
              className="back-button"
              onClick={() => setActiveSection(null)}
            >
              ← Назад
            </button>

            <ClientForm
              clients={clients}
              onCreate={handleCreateClient}
              onError={showError}
            />

            <ClientTable
              clients={clients}
              onDelete={handleDeleteClient}
            />
          </section>
        )}

        {activeSection === "masters" && (
          <section className="window-card purple-bg">
            <button
              className="back-button"
              onClick={() => setActiveSection(null)}
            >
              ← Назад
            </button>

            <MasterForm
              onCreate={handleCreateMaster}
              onError={showError}
            />

            <MasterTable
              masters={masters}
              onDelete={handleDeleteMaster}
            />
          </section>
        )}

        {activeSection === "services" && (
          <section className="window-card pink-bg">
            <button
              className="back-button"
              onClick={() => setActiveSection(null)}
            >
              ← Назад
            </button>

            <ServiceForm
              onCreate={handleCreateService}
              onError={showError}
            />

            <ServiceTable
              services={services}
              onDelete={handleDeleteService}
            />
          </section>
        )}

        {activeSection === "visits" && (
          <section className="window-card green-bg">
            <button
              className="back-button"
              onClick={() => setActiveSection(null)}
            >
              ← Назад
            </button>

            <VisitForm
              clients={clients}
              masters={masters}
              services={services}
              onCreate={handleCreateVisit}
            />

            <VisitTable visits={visits} />
          </section>
        )}

        <footer className="app-footer">
          Beauty Salon © 2026 — информационная система управления парикмахерской
        </footer>
      </main>
    </>
  );
}