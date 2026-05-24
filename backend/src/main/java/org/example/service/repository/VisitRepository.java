package org.example.service.repository;

import org.example.entity.Visit;
import ru.tinkoff.kora.database.common.annotation.Query;
import ru.tinkoff.kora.database.common.annotation.Repository;
import ru.tinkoff.kora.database.jdbc.JdbcRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitRepository extends JdbcRepository {

    @Query("""
            SELECT id, client_id, master_id, visit_date, total_cost
            FROM visits
            """)
    List<Visit> findAll();

    @Query("""
            INSERT INTO visits(client_id, master_id, visit_date, total_cost)
            VALUES (:clientId, :masterId, :visitDate, :totalCost)
            RETURNING id, client_id, master_id, visit_date, total_cost
            """)
    Visit create(Long clientId, Long masterId, LocalDate visitDate, Double totalCost);

    @Query("""
            SELECT u.full_name
            FROM visits v
            JOIN users u ON u.id = v.client_id
            WHERE v.visit_date = :date
            """)
    List<String> findClientNamesByDate(LocalDate date);

    @Query("""
            SELECT COALESCE(SUM(total_cost), 0)
            FROM visits
            WHERE master_id = :masterId
            """)
    Double getMasterIncome(Long masterId);

    @Query("""
            SELECT u.full_name
            FROM visits v
            JOIN users u ON u.id = v.master_id
            WHERE u.role = 'MASTER'
            GROUP BY u.id, u.full_name
            ORDER BY COUNT(*) DESC
            LIMIT 1
            """)
    String getTopMasterName();

    @Query("""
            SELECT COUNT(*)
            FROM visits v
            JOIN users u ON u.id = v.master_id
            WHERE u.role = 'MASTER'
            GROUP BY u.id
            ORDER BY COUNT(*) DESC
            LIMIT 1
            """)
    Long getTopMasterVisitsCount();
}