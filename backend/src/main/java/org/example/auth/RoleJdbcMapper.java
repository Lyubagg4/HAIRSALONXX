package org.example.auth;

import ru.tinkoff.kora.common.Component;
import ru.tinkoff.kora.database.jdbc.mapper.result.JdbcResultColumnMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class RoleJdbcMapper implements JdbcResultColumnMapper<Role> {

    @Override
    public Role apply(ResultSet rs, int index) throws SQLException {
        String value = rs.getString(index);

        if (value == null) {
            return null;
        }

        return Role.valueOf(value);
    }
}