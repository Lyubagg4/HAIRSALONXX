package org.example.service.repository;

import org.example.entity.HairService;
import ru.tinkoff.kora.database.common.annotation.Query;
import ru.tinkoff.kora.database.common.annotation.Repository;
import ru.tinkoff.kora.database.jdbc.JdbcRepository;

import java.util.List;

@Repository
public interface HairServiceRepository extends JdbcRepository {

    @Query("""
            SELECT id, name, category, type, hall_type, price
            FROM services
            ORDER BY category, name
            """)
    List<HairService> findAll();

    @Query("""
            SELECT id, name, category, type, hall_type, price
            FROM services
            WHERE id = :id
            """)
    HairService findById(Long id);

    @Query("""
            INSERT INTO services(name, category, type, hall_type, price)
            VALUES (:name, :category, :type, :hallType, :price)
            RETURNING id, name, category, type, hall_type, price
            """)
    HairService create(
            String name,
            String category,
            String type,
            String hallType,
            Double price
    );

    @Query("""
            UPDATE services
            SET name = :name,
                category = :category,
                type = :type,
                hall_type = :hallType,
                price = :price
            WHERE id = :id
            RETURNING id, name, category, type, hall_type, price
            """)
    HairService update(
            Long id,
            String name,
            String category,
            String type,
            String hallType,
            Double price
    );

    @Query("""
            DELETE FROM services
            WHERE id = :id
            """)
    void deleteById(Long id);
}