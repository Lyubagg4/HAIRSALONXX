package org.example.service;

import org.example.entity.HairService;
import org.example.entity.Visit;
import org.example.entity.dto.VisitRequest;
import org.example.entity.dto.VisitResponse;
import org.example.exception.AppException;
import org.example.service.repository.HairServiceRepository;
import org.example.service.repository.UserRepository;
import org.example.service.repository.VisitRepository;
import org.example.service.repository.VisitServiceRepository;
import ru.tinkoff.kora.common.Component;

import java.util.List;

@Component
public class VisitService {

    private final VisitRepository visitRepository;
    private final VisitServiceRepository visitServiceRepository;
    private final HairServiceRepository hairServiceRepository;
    private final UserRepository userRepository;

    public VisitService(
            VisitRepository visitRepository,
            VisitServiceRepository visitServiceRepository,
            HairServiceRepository hairServiceRepository,
            UserRepository userRepository
    ) {
        this.visitRepository = visitRepository;
        this.visitServiceRepository = visitServiceRepository;
        this.hairServiceRepository = hairServiceRepository;
        this.userRepository = userRepository;
    }

    public List<VisitResponse> getAllVisits() {
        return visitRepository.findAll()
                .stream()
                .map(visit -> new VisitResponse(
                        visit.id(),
                        visit.clientId(),
                        visit.masterId(),
                        visit.visitDate(),
                        visit.totalCost(),
                        List.of()
                ))
                .toList();
    }

    public VisitResponse createVisit(VisitRequest request) {
        if (request.clientId() == null) {
            throw new AppException("Выберите клиента");
        }

        if (request.masterId() == null) {
            throw new AppException("Выберите мастера");
        }

        if (request.visitDate() == null) {
            throw new AppException("Выберите дату визита");
        }

        if (request.serviceIds() == null || request.serviceIds().isEmpty()) {
            throw new AppException("Выберите хотя бы одну услугу");
        }

        var client = userRepository.findById(request.clientId());

        double servicesSum = request.serviceIds()
                .stream()
                .map(hairServiceRepository::findById)
                .mapToDouble(HairService::price)
                .sum();

        double discount = client.discount() == null ? 0.0 : client.discount();
        double totalCost = servicesSum - (servicesSum * discount / 100.0);

        Visit visit = visitRepository.create(
                request.clientId(),
                request.masterId(),
                request.visitDate(),
                totalCost
        );

        for (Long serviceId : request.serviceIds()) {
            HairService service = hairServiceRepository.findById(serviceId);

            visitServiceRepository.create(
                    visit.id(),
                    service.id(),
                    service.price()
            );
        }

        return new VisitResponse(
                visit.id(),
                visit.clientId(),
                visit.masterId(),
                visit.visitDate(),
                visit.totalCost(),
                request.serviceIds()
        );
    }
}