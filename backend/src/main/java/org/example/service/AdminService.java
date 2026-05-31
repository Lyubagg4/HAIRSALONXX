package org.example.service;

import org.example.entity.dto.AdminRequest;
import org.example.entity.dto.AdminResponse;
import org.example.entity.User;
import org.example.service.repository.UserRepository;
import ru.tinkoff.kora.common.Component;

@Component
public class AdminService {

    private final UserRepository userRepository;

    public AdminService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AdminResponse createAdmin(AdminRequest request) {
        User admin = userRepository.createAdmin(
                request.fullName(),
                request.phone(),
                request.password()
        );

        return new AdminResponse(
                admin.id(),
                admin.fullName(),
                admin.phone(),
                admin.role().name()
        );
    }
}
