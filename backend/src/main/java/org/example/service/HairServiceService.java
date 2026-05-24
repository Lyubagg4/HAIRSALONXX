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

        hairServiceRepository.create(
                request.name(),
                request.type(),
                request.hallType(),
                request.price()
        );

        return new HairServiceResponse(
                null,
                request.name(),
                request.type(),
                request.hallType(),
                request.price()
        );
    }

    public HairServiceResponse updateService(Long id, HairServiceRequest request) {
        validateService(request);

        hairServiceRepository.update(
                id,
                request.name(),
                request.type(),
                request.hallType(),
                request.price()
        );

        return toResponse(hairServiceRepository.findById(id));
    }

    public void deleteService(Long id) {
        hairServiceRepository.deleteById(id);
    }

    private void validateService(HairServiceRequest request) {
        validationService.requireText(request.name(), "Название услуги");
        validationService.requireText(request.type(), "Тип услуги");
        validationService.requireText(request.hallType(), "Зал");
        validationService.validatePrice(request.price());
    }

    private HairServiceResponse toResponse(HairService service) {
        return new HairServiceResponse(
                service.id(),
                service.name(),
                service.type(),
                service.hallType(),
                service.price()
        );
    }
}