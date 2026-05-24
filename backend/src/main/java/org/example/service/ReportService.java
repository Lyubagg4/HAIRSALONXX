package org.example.service;

import org.example.entity.dto.ReportResponse;
import org.example.service.repository.UserRepository;
import org.example.service.repository.VisitRepository;
import org.example.service.repository.VisitServiceRepository;
import ru.tinkoff.kora.common.Component;
import ru.tinkoff.kora.http.common.annotation.Query;


import java.time.LocalDate;
import java.util.List;

@Component
public class ReportService {

    private final VisitRepository visitRepository;
    private final VisitServiceRepository visitServiceRepository;
    private final UserRepository userRepository;

    public ReportService(
            VisitRepository visitRepository,
            VisitServiceRepository visitServiceRepository,
            UserRepository userRepository
    ) {
        this.visitRepository = visitRepository;
        this.visitServiceRepository = visitServiceRepository;
        this.userRepository = userRepository;
    }

    public List<ReportResponse> getClientsByDate(LocalDate date) {
        return visitRepository.findClientNamesByDate(date)
                .stream()
                .map(name -> new ReportResponse("Клиент", name))
                .toList();
    }

    public ReportResponse getMasterIncome(Long masterId) {
        Double income = visitRepository.getMasterIncome(masterId);
        return new ReportResponse("Заработок мастера", String.valueOf(income));
    }

    public ReportResponse getPopularService() {
        String serviceName = visitServiceRepository.getPopularServiceName();
        return new ReportResponse("Популярная услуга", serviceName);
    }

    public ReportResponse getGenderStats() {
        Long maleCount = userRepository.getMaleClientsCount();
        Long femaleCount = userRepository.getFemaleClientsCount();

        return new ReportResponse(
                "Соотношение клиентов по полу",
                "Мужчин: " + maleCount + ", женщин: " + femaleCount
        );
    }

    public ReportResponse getRegularClientsCount() {
        Long count = userRepository.getRegularClientsCount();
        return new ReportResponse("Количество постоянных клиентов", String.valueOf(count));
    }

    public ReportResponse getTopMaster() {
        String name = visitRepository.getTopMasterName();
        Long count = visitRepository.getTopMasterVisitsCount();

        return new ReportResponse(
                "Лучший мастер",
                name + ", обслужено клиентов: " + count
        );
    }
}