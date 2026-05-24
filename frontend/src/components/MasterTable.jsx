export default function MasterTable({ masters = [], onDelete }) {
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

  function specializationLabel(spec) {
    switch (spec) {
      case "FEMALE_HALL":
        return "Женский зал";
      case "MALE_HALL":
        return "Мужской зал";
      default:
        return spec || "-";
    }
  }

  function qualificationLabel(q) {
    switch (q) {
      case "HAIRDRESSER":
        return "Парикмахер";
      case "BARBER":
        return "Барбер";
      case "NAIL_MASTER":
        return "Ногтевой сервис";
      case "COLORIST":
        return "Колорист";
      case "STYLIST":
        return "Стилист";
      default:
        return q || "-";
    }
  }

  return (
    <div>
      <h2>Мастера</h2>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>ФИО</th>
            <th>Телефон</th>
            <th>Пол</th>
            <th>Специализация</th>
            <th>Квалификация</th>
            <th></th>
          </tr>
        </thead>

        <tbody>
          {masters.map((master) => (
            <tr key={master.id}>
              <td>{master.id}</td>
              <td>{master.fullName}</td>
              <td>{master.phone}</td>
              <td>{genderLabel(master.gender)}</td>
              <td>{specializationLabel(master.specialization)}</td>
              <td>{qualificationLabel(master.qualification)}</td>

              <td>
                <button onClick={() => onDelete(master.id)}>
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