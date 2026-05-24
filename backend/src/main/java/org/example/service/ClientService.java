package org.example.service;

import org.example.entity.User;
import org.example.entity.dto.ClientRequest;
import org.example.entity.dto.ClientResponse;
import org.example.exception.AppException;
import org.example.service.repository.UserRepository;
import ru.tinkoff.kora.common.Component;

import java.util.List;

@Component
public class ClientService {

    private final UserRepository userRepository;
    private final ValidationService validationService;

    public ClientService(UserRepository userRepository, ValidationService validationService) {
        this.userRepository = userRepository;
        this.validationService = validationService;
    }

    public List<ClientResponse> getAllClients() {
        return userRepository.findAllClients()
                .stream()
                .map(this::toClientResponse)
                .toList();
    }

    public ClientResponse createClient(ClientRequest request) {
        try {
            User user = userRepository.createClient(
                    request.fullName(),
                    request.phone(),
                    request.gender(),
                    request.category(),
                    request.discount()
            );

            return toClientResponse(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public ClientResponse updateClient(Long id, ClientRequest request) {
        validateClient(request);

        User existing = findByPhoneSafe(request.phone());

        if (existing != null && !existing.id().equals(id)) {
            throw new AppException("Пользователь с таким телефоном уже существует");
        }

        userRepository.updateClient(
                id,
                request.fullName(),
                request.phone(),
                request.gender(),
                request.category(),
                request.discount()
        );

        return toClientResponse(userRepository.findById(id));
    }

    public void deleteClient(Long id) {
        userRepository.deleteById(id);
    }

    private void validateClient(ClientRequest request) {
        validationService.requireText(request.fullName(), "ФИО");
        validationService.validatePhone(request.phone());
        validationService.requireText(request.gender(), "Пол");
        validationService.requireText(request.category(), "Категория клиента");
        validationService.validateDiscount(request.discount());
    }

    private User findByPhoneSafe(String phone) {
        try {
            return userRepository.findByPhone(phone);
        } catch (Exception e) {
            return null;
        }
    }

    private ClientResponse toClientResponse(User user) {
        return new ClientResponse(
                user.id(),
                user.fullName(),
                user.phone(),
                user.gender(),
                user.category(),
                user.discount()
        );
    }
}