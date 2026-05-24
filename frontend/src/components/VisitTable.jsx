export default function VisitTable({ visits }) {
  return (
    <div>
      <h2>Визиты</h2>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Клиент ID</th>
            <th>Мастер ID</th>
            <th>Дата</th>
            <th>Стоимость</th>
            <th>Услуги</th>
          </tr>
        </thead>

        <tbody>
          {visits.map((visit) => (
            <tr key={visit.id}>
              <td>{visit.id}</td>
              <td>{visit.clientId}</td>
              <td>{visit.masterId}</td>
              <td>{visit.visitDate}</td>
              <td>{visit.totalCost}</td>
              <td>{visit.serviceIds?.join(", ")}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}