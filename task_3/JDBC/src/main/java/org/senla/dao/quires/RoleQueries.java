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
            INSERT INTO roles (name)
            VALUES  (?);
            """;

    public static final String UPDATE_PRIMARY_INFO_BY_ID = """
            UPDATE roles
            SET name = ?
            WHERE id = ?;
            """;

    public static final String DELETE_PRIMARY_INFO_BY_ID = """
            DELETE FROM roles
            WHERE id = ?;
            """;

}
