package org.senla.dao.quires;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleQueries {
    public static final String GET_BY_ID = """
            SELECT id, name
            FROM roles
            WHERE id = ?;
            """;

    public static final String GET_ALL = """
            SELECT id, name
            FROM roles;
            """;

    public static final String INSERT = """
            INSERT INTO roles (id, name)
            VALUES (?, ?);
            """;

    public static final String UPDATE = """
            UPDATE roles
            SET id = ?, name = ?
            WHERE id = ?;
            """;

    public static final String DELETE = """
            DELETE FROM roles
            WHERE id = ?;
            """;

}
