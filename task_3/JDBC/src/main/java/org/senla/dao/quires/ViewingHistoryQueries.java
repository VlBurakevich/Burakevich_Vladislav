package org.senla.dao.quires;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ViewingHistoryQueries {
    public static final String GET_BY_ID = """
            SELECT id, user_id, movie_id, watched_at
            FROM viewing_history
            WHERE id = ?;
            """;

    public static final String GET_ALL = """
            SELECT id, user_id, movie_id, watched_at
            FROM viewing_history;
            """;

    public static final String INSERT = """
            INSERT INTO viewing_history (user_id, movie_id, watched_at)
            VALUES (?, ?, ?);
            """;

    public static final String UPDATE = """
            UPDATE viewing_history
            SET user_id = ?, movie_id = ?, watched_at = ?
            WHERE id = ?;
            """;

    public static final String DELETE = """
            DELETE FROM viewing_history
            WHERE id = ?;
            """;

}
