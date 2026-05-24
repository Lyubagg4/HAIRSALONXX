package org.example.service.repository;

import ru.tinkoff.kora.database.common.annotation.Query;
import ru.tinkoff.kora.database.common.annotation.Repository;
import ru.tinkoff.kora.database.jdbc.JdbcRepository;

@Repository
public interface VisitServiceRepository extends JdbcRepository {

    @Query("""
            INSERT INTO visit_service(visit_id, service_id, price_at_time)
            VALUES (:visitId, :serviceId, :priceAtTime)
            """)
    void create(Long visitId, Long serviceId, Double priceAtTime);

    @Query("""
            SELECT s.name
            FROM visit_service vs
            JOIN services s ON s.id = vs.service_id
            GROUP BY s.id, s.name
            ORDER BY COUNT(*) DESC
            LIMIT 1
            """)
    String getPopularServiceName();
}