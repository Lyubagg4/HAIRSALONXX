export default function ClientTable({ clients = [], onDelete }) {
  function genderLabel(gender) {
    switch (gender) {
      case "FEMALE":
        return "Жен.";
      case "MALE":
        return "Муж.";
      default:
        return gender || "-";
    }
  }

  function categoryLabel(category) {
    switch (category) {
      case "REGULAR":
        return "Постоянный";
      case "RANDOM":
        return "Случайный";
      default:
        return category || "-";
    }
  }

  return (
    <div>
      <h2>Клиенты</h2>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>ФИО</th>
            <th>Телефон</th>
            <th>Пол</th>
            <th>Категория</th>
            <th>Скидка</th>
            <th></th>
          </tr>
        </thead>

        <tbody>
          {clients.map((client) => (
            <tr key={client.id}>
              <td>{client.id}</td>
              <td>{client.fullName}</td>
              <td>{client.phone}</td>
              <td>{genderLabel(client.gender)}</td>
              <td>{categoryLabel(client.category)}</td>
              <td>{client.discount}%</td>
              <td>
                <button onClick={() => onDelete(client.id)}>
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