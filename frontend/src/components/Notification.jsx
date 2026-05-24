import { useEffect } from "react";

export default function Notification({ notification, onClose }) {
  useEffect(() => {
    if (!notification || notification.type === "confirm") {
      return;
    }

    const timer = setTimeout(() => {
      onClose();
    }, 3000);

    return () => clearTimeout(timer);
  }, [notification, onClose]);

  if (!notification) {
    return null;
  }

  if (notification.type === "confirm") {
    return (
      <div className="confirm-overlay">
        <div className="confirm-card">
          <h3>{notification.title}</h3>
          <p>{notification.message}</p>

          <div className="confirm-actions">
            <button className="cancel-btn" onClick={onClose}>
              Отмена
            </button>

            <button
              className="danger-btn"
              onClick={async () => {
                const action = notification.onConfirm;
                onClose();
                await action();
              }}
            >
              Удалить
            </button>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className={`notification notification-${notification.type}`}>
      <div className="notification-content">
        <strong>{notification.title}</strong>
        <p>{notification.message}</p>
      </div>

      <button className="notification-close" onClick={onClose}>
        ×
      </button>
    </div>
  );
}