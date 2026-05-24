export default function ServiceTable({ services = [], onDelete }) {
  function typeLabel(type) {
    switch (type) {
      case "HAIRCUT":
        return "Стрижка";
      case "COLORING":
        return "Окрашивание";
      case "STYLING":
        return "Укладка";
      default:
        return type || "-";
    }
  }

  function hallLabel(hallType) {
    switch (hallType) {
      case "FEMALE_HALL":
        return "Женский зал";
      case "MALE_HALL":
        return "Мужской зал";
      default:
        return hallType || "-";
    }
  }

  return (
    <div>
      <h2>Услуги</h2>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Название</th>
            <th>Тип</th>
            <th>Зал</th>
            <th>Цена</th>
            <th></th>
          </tr>
        </thead>

        <tbody>
          {services.map((service) => (
            <tr key={service.id}>
              <td>{service.id}</td>
              <td>{service.name}</td>
              <td>{typeLabel(service.type)}</td>
              <td>{hallLabel(service.hallType)}</td>
              <td>{service.price} ₽</td>
              <td>
                <button onClick={() => onDelete(service.id)}>
                  Удалить
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}