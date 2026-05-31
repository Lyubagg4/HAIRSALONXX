package org.example.entity.util;

import org.example.entity.ServiceCategory;
import ru.tinkoff.kora.common.Component;
import ru.tinkoff.kora.database.jdbc.mapper.result.JdbcResultColumnMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ServiceCategoryJdbcMapper implements JdbcResultColumnMapper<ServiceCategory> {

    @Override
    public ServiceCategory apply(ResultSet rs, int index) throws SQLException {
        String value = rs.getString(index);

        if (value == null) {
            return null;
        }

        return ServiceCategory.valueOf(value);
    }
}
