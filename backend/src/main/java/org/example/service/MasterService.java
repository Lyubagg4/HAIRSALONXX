package org.example.service;

import org.example.entity.User;
import org.example.entity.dto.MasterRequest;
import org.example.entity.dto.MasterResponse;
import org.example.exception.AppException;
import org.example.service.repository.UserRepository;
import ru.tinkoff.kora.common.Component;

import java.util.List;

@Component
public class MasterService {

    private final UserRepository userRepository;
    private final ValidationService validationService;

    public MasterService(UserRepository userRepository, ValidationService validationService) {
        this.userRepository = userRepository;
        this.validationService = validationService;
    }

    public List<MasterResponse> getAllMasters() {
        return userRepository.findAllMasters()
                .stream()
                .map(this::toMasterResponse)
                .toList();
    }

    public MasterResponse createMaster(MasterRequest request) {
        validateMaster(request);

        User existing = findByPhoneSafe(request.phone());

        if (existing != null) {
            throw new AppException("Пользователь с таким телефоном уже существует");
        }

        userRepository.createMaster(
                request.fullName(),
                request.phone(),
                request.gender(),
                request.specialization(),
                request.qualification()
        );

        User user = userRepository.findByPhone(request.phone());
        return toMasterResponse(user);
    }

    public MasterResponse updateMaster(Long id, MasterRequest request) {
        validateMaster(request);

        User existing = findByPhoneSafe(request.phone());

        if (existing != null && !existing.id().equals(id)) {
            throw new AppException("Пользователь с таким телефоном уже существует");
        }

        userRepository.updateMaster(
                id,
                request.fullName(),
                request.phone(),
                request.gender(),
                request.specialization(),
                request.qualification()
        );

        return toMasterResponse(userRepository.findById(id));
    }

    public void deleteMaster(Long id) {
        userRepository.deleteById(id);
    }

    private void validateMaster(MasterRequest request) {
        validationService.requireText(request.fullName(), "ФИО");
        validationService.validatePhone(request.phone());
        validationService.requireText(request.gender(), "Пол");
        validationService.requireText(request.specialization(), "Специализация");
        validationService.requireText(request.qualification(), "Квалификация");
    }

    private User findByPhoneSafe(String phone) {
        try {
            return userRepository.findByPhone(phone);
        } catch (Exception e) {
            return null;
        }
    }

    private MasterResponse toMasterResponse(User user) {
        return new MasterResponse(
                user.id(),
                user.fullName(),
                user.phone(),
                user.gender(),
                user.specialization(),
                user.qualification()
        );
    }
}