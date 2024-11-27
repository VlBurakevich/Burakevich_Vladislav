package org.senla.dao.quires;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserQueries {
    public static final String GET_BY_ID = """
            SELECT id, username
            FROM users
            WHERE id = ?;
            """;

    public static final String GET_ALL = """
            SELECT id, username
            FROM users;
            """;

    public static final String INSERT = """
            INSERT INTO users (username)
            VALUES (?);
            """;

    public static final String UPDATE = """
            UPDATE users
            SET username = ?
            WHERE id = ?;
            """;

    public static final String DELETE = """
            DELETE FROM users
            WHERE id = ?;
            """;

}
