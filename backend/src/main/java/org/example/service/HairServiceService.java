package org.example.service;

import org.example.entity.HairService;
import org.example.entity.dto.HairServiceRequest;
import org.example.entity.dto.HairServiceResponse;
import org.example.service.repository.HairServiceRepository;
import ru.tinkoff.kora.common.Component;

import java.util.List;

@Component
public class HairServiceService {

    private final HairServiceRepository hairServiceRepository;
    private final ValidationService validationService;

    public HairServiceService(
            HairServiceRepository hairServiceRepository,
            ValidationService validationService
    ) {
        this.hairServiceRepository = hairServiceRepository;
        this.validationService = validationService;
    }

    public List<HairServiceResponse> getAllServices() {
        return hairServiceRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public HairServiceResponse createService(HairServiceRequest request) {
        validateService(request);

        HairService service = hairServiceRepository.create(
                request.name(),
                request.category(),
                request.type(),
                request.hallType(),
                request.price()
        );

        return toResponse(service);
    }

    public HairServiceResponse updateService(Long id, HairServiceRequest request) {
        validateService(request);

        HairService service = hairServiceRepository.update(
                id,
                request.name(),
                request.category(),
                request.type(),
                request.hallType(),
                request.price()
        );

        return toResponse(service);
    }

    public void deleteService(Long id) {
        hairServiceRepository.deleteById(id);
    }

    private void validateService(HairServiceRequest request) {
        validationService.requireText(request.name(), "Название услуги");
        validationService.requireText(request.category(), "Категория услуги");
        validationService.requireText(request.type(), "Тип услуги");
        validationService.requireText(request.hallType(), "Зал");
        validationService.validatePrice(request.price());
    }

    private HairServiceResponse toResponse(HairService service) {
        return new HairServiceResponse(
                service.id(),
                service.name(),
                service.category(),
                service.type(),
                service.hallType(),
                service.price()
        );
    }
}