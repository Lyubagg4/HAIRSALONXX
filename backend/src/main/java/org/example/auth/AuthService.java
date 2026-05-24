package org.example.auth;

import org.example.entity.User;
import org.example.service.repository.UserRepository;
import ru.tinkoff.kora.common.Component;

@Component
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        try {
            System.out.println("LOGIN REQUEST: " + request.phone() + " / " + request.password());

            User user = userRepository.findByPhone(request.phone());

            System.out.println("USER FROM DB: " + user);

            if (user == null) {
                throw new RuntimeException("Пользователь не найден");
            }

            if (!request.password().equals(user.passwordHash())) {
                throw new RuntimeException("Неверный пароль");
            }

            String token = jwtService.generateToken(user.id(), user.role());

            return new LoginResponse(token, user.role().name());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}