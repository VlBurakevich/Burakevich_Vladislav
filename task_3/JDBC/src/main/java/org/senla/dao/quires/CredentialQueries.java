package org.senla.dao.quires;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CredentialQueries {
    public static final String GET_BY_ID = """
            SELECT id, user_id, password, email
            FROM credentials
            WHERE id = ?;
            """;

    public static final String GET_ALL = """
            SELECT id, user_id, password, email
            FROM credentials;
            """;

    public static final String INSERT = """
            INSERT INTO credentials (user_id, password, email)
            VALUES (?, ?, ?);
            """;

    public static final String UPDATE = """
            UPDATE credentials
            SET user_id = ?, password = ?, email = ?
            WHERE id = ?;
            """;

    public static final String DELETE = """
            DELETE FROM credentials
            WHERE id = ?;
            """;
}
