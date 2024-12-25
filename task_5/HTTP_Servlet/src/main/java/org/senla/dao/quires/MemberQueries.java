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
            INSERT INTO members (first_name, last_name, nationality, type, gender)
            VALUES (?, ?, ?, ?::member_type, ?::gender_type);
            """;

    public static final String UPDATE_PRIMARY_INFO_BY_ID = """
            UPDATE members
            SET first_name = ?, last_name = ?, nationality = ?, gender = ?
            WHERE id = ?;
            """;

    public static final String DELETE_PRIMARY_INFO_BY_ID = """
            DELETE FROM members
            WHERE id = ?;
            """;

    public static final String GET_BY_MOVIE_ID = """
            SELECT m.id, m.first_name, m.last_name, m.nationality, m.gender
            FROM members m
            JOIN movie_member mm ON m.id = mm.member_id
            WHERE mm.movie_id = ?;
            """;
    public static final String INSERT_MOVIE_MEMBER_LINK = """
            INSERT INTO movie_member (movie_id, member_id)
            VALUES (?, ?);
            """;
}
