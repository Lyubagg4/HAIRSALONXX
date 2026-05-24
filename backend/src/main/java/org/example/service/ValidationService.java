package org.example.service;

import org.example.exception.AppException;
import ru.tinkoff.kora.common.Component;

@Component
public class ValidationService {

    public void requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new AppException(fieldName + " не может быть пустым");
        }
    }

    public void validatePhone(String phone) {
        requireText(phone, "Телефон");

        if (!phone.matches("^[0-9+]{5,15}$")) {
            throw new AppException("Телефон должен содержать только цифры или + и быть длиной от 5 до 15 символов");
        }
    }

    public void validateDiscount(Double discount) {
        if (discount == null) {
            return;
        }

        if (discount < 0 || discount > 100) {
            throw new AppException("Скидка должна быть от 0 до 100");
        }
    }

    public void validatePrice(Double price) {
        if (price == null || price <= 0) {
            throw new AppException("Цена должна быть больше 0");
        }
    }
}