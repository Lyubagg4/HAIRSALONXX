package org.example.entity.util;

import org.example.entity.Qualification;
import ru.tinkoff.kora.common.Component;
import ru.tinkoff.kora.database.jdbc.mapper.result.JdbcResultColumnMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class QJdbcMapper implements JdbcResultColumnMapper<Qualification> {

    @Override
    public Qualification apply(ResultSet rs, int index) throws SQLException {
        String value = rs.getString(index);

        if (value == null) {
            return null;
        }

        return Qualification.valueOf(value);
    }
}
