package org.example.service.repository;

import ru.tinkoff.kora.database.common.annotation.Query;
import ru.tinkoff.kora.database.common.annotation.Repository;
import ru.tinkoff.kora.database.jdbc.JdbcRepository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository extends JdbcRepository {

    @Query("""
            SELECT COALESCE((
                SELECT s.name
                FROM visit_service vs
                JOIN services s ON s.id = vs.service_id
                GROUP BY s.name
                ORDER BY COUNT(*) DESC
                LIMIT 1
            ), 'Нет данных')
            """)
    String getPopularService();

    @Query("""
            SELECT CONCAT(
                'Мужчин: ',
                COUNT(*) FILTER (WHERE gender = 'MALE'),
                ', Женщин: ',
                COUNT(*) FILTER (WHERE gender = 'FEMALE')
            )
            FROM users
            WHERE role = 'CLIENT'
            """)
    String getGenderStats();

    @Query("""
            SELECT COUNT(*)
            FROM users
            WHERE category = 'REGULAR'
              AND role = 'CLIENT'
            """)
    Integer getRegularClientsCount();

    @Query("""
            SELECT COALESCE((
                SELECT u.full_name
                FROM visits v
                JOIN users u ON u.id = v.master_id
                GROUP BY u.full_name
                ORDER BY SUM(v.total_cost) DESC
                LIMIT 1
            ), 'Нет данных')
            """)
    String getTopMaster();

    @Query("""
            SELECT COALESCE(SUM(total_cost), 0)
            FROM visits
            WHERE master_id = :masterId
            """)
    Double getMasterIncome(Long masterId);

    @Query("""
            SELECT full_name
            FROM users
            WHERE id IN (
                SELECT client_id
                FROM visits
                WHERE visit_date = :date
            )
            ORDER BY full_name
            """)
    List<String> getClientsByDate(LocalDate date);
}