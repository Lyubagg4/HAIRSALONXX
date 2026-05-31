package org.example.service;

import org.example.entity.dto.ReportResponse;
import org.example.service.repository.ReportRepository;
import ru.tinkoff.kora.common.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public ReportResponse getPopularService() {
        String value = reportRepository.getPopularService();

        return new ReportResponse(
                "Популярная услуга",
                value == null ? "Нет данных" : value
        );
    }

    public ReportResponse getGenderStats() {
        String value = reportRepository.getGenderStats();

        return new ReportResponse(
                "Соотношение клиентов",
                value == null ? "Нет данных" : value
        );
    }

    public ReportResponse getRegularClientsCount() {
        Integer value = reportRepository.getRegularClientsCount();

        return new ReportResponse(
                "Постоянные клиенты",
                value == null ? "0" : String.valueOf(value)
        );
    }

    public ReportResponse getTopMaster() {
        String value = reportRepository.getTopMaster();

        return new ReportResponse(
                "Лучший мастер",
                value == null ? "Нет данных" : value
        );
    }

    public ReportResponse getMasterIncome(Long masterId) {
        Double value = reportRepository.getMasterIncome(masterId);

        return new ReportResponse(
                "Доход мастера",
                value == null ? "0 ₽" : value + " ₽"
        );
    }

    public List<ReportResponse> getClientsByDate(LocalDate date) {
        return reportRepository.getClientsByDate(date)
                .stream()
                .map(client -> new ReportResponse(
                        "Клиент",
                        client
                ))
                .toList();
    }
}