package org.example.service.repository;

import org.example.entity.User;
import ru.tinkoff.kora.database.common.annotation.Query;
import ru.tinkoff.kora.database.common.annotation.Repository;
import ru.tinkoff.kora.database.jdbc.JdbcRepository;

import java.util.List;

@Repository
public interface UserRepository extends JdbcRepository {

    @Query("""
            SELECT id, full_name, phone, gender, category, discount,
                   specialization, qualification, password_hash, role
            FROM users
            WHERE role = 'CLIENT'
            """)
    List<User> findAllClients();

    @Query("""
            SELECT id, full_name, phone, gender, category, discount,
                   specialization, qualification, password_hash, role
            FROM users
            WHERE role = 'MASTER'
            """)
    List<User> findAllMasters();

    @Query("""
            SELECT id, full_name, phone, gender, category, discount,
                   specialization, qualification, password_hash, role
            FROM users
            WHERE id = :id
            """)
    User findById(Long id);

    @Query("""
            SELECT id, full_name, phone, gender, category, discount,
                   specialization, qualification, password_hash, role
            FROM users
            WHERE phone = :phone
            """)
    User findByPhone(String phone);

    @Query("""
        INSERT INTO users(full_name, phone, gender, category, discount, role)
        VALUES (:fullName, :phone, :gender, :category, :discount, 'CLIENT')
        RETURNING id, full_name, phone, gender, category, discount,
                  specialization, qualification, password_hash, role
        """)
    User createClient(String fullName, String phone, String gender, String category, Double discount);

    @Query("""
            INSERT INTO users(full_name, phone, gender, specialization, qualification, role)
            VALUES (:fullName, :phone, :gender, :specialization, :qualification, 'MASTER')
            """)
    void createMaster(String fullName, String phone, String gender, String specialization, String qualification);

    @Query("""
            UPDATE users
            SET full_name = :fullName,
                phone = :phone,
                gender = :gender,
                category = :category,
                discount = :discount
            WHERE id = :id
            """)
    void updateClient(Long id, String fullName, String phone, String gender, String category, Double discount);

    @Query("""
            UPDATE users
            SET full_name = :fullName,
                phone = :phone,
                gender = :gender,
                specialization = :specialization,
                qualification = :qualification
            WHERE id = :id
            """)
    void updateMaster(Long id, String fullName, String phone, String gender, String specialization, String qualification);

    @Query("DELETE FROM users WHERE id = :id")
    void deleteById(Long id);

    @Query("""
            SELECT COUNT(*)
            FROM users
            WHERE role = 'CLIENT'
              AND category = 'REGULAR'
            """)
    Long getRegularClientsCount();

    @Query("""
            SELECT COUNT(*)
            FROM users
            WHERE role = 'CLIENT'
              AND gender = 'MALE'
            """)
    Long getMaleClientsCount();

    @Query("""
            SELECT COUNT(*)
            FROM users
            WHERE role = 'CLIENT'
              AND gender = 'FEMALE'
            """)
    Long getFemaleClientsCount();
}