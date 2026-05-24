package org.example.service.repository;

import org.example.entity.HairService;
import ru.tinkoff.kora.database.common.annotation.Query;
import ru.tinkoff.kora.database.common.annotation.Repository;
import ru.tinkoff.kora.database.jdbc.JdbcRepository;

import java.util.List;

@Repository
public interface HairServiceRepository extends JdbcRepository {

    @Query("SELECT id, name, type, hall_type, price FROM services")
    List<HairService> findAll();

    @Query("SELECT id, name, type, hall_type, price FROM services WHERE id = :id")
    HairService findById(Long id);

    @Query("""
            INSERT INTO services(name, type, hall_type, price)
            VALUES (:name, :type, :hallType, :price)
            """)
    void create(String name, String type, String hallType, Double price);

    @Query("""
            UPDATE services
            SET name = :name,
                type = :type,
                hall_type = :hallType,
                price = :price
            WHERE id = :id
            """)
    void update(Long id, String name, String type, String hallType, Double price);

    @Query("DELETE FROM services WHERE id = :id")
    void deleteById(Long id);
}