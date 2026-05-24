export default function ReportCard({ report, color = "blue" }) {
  return (
    <div className={`report-card ${color}`}>
      <h3>{report?.title || "Отчёт"}</h3>
      <p>{report?.value || "Нет данных"}</p>
    </div>
  );
}