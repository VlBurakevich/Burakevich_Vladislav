package org.senla.dao.quires;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MemberQueries {
    public static final String GET_BY_ID = """
            SELECT id, first_name, last_name, nationality, gender
            FROM members
            WHERE id = ?;
            """;

    public static final String GET_ALL = """
            SELECT id, first_name, last_name, nationality, gender
            FROM members;
            """;

    public static final String INSERT = """
            INSERT INTO members (id, first_name, last_name, nationality, gender)
                VALUES (?, ?, ?, ?, ?);
            """;

    public static final String UPDATE = """
            UPDATE members
            SET first_name = ?, last_name = ?, nationality = ?, gender = ?
            WHERE id = ?;
            """;

    public static final String DELETE = """
            DELETE FROM members
            WHERE id = ?;
            """;

}
