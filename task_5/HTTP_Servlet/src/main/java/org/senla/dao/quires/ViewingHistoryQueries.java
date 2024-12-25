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

    public static final String UPDATE_PRIMARY_INFO_BY_ID = """
            UPDATE viewing_history
            SET user_id = ?, movie_id = ?, watched_at = ?
            WHERE id = ?;
            """;

    public static final String DELETE_PRIMARY_INFO_BY_ID = """
            DELETE FROM viewing_history
            WHERE id = ?;
            """;

    public static final String DELETE_BY_USER_AND_MOVIE = """
            DELETE FROM viewing_history
            WHERE user_id = ? AND movie_id = ?;
            """;
    public static final String GET_MOVIES_BY_USER_ID = """
            SELECT m.id, m.title, m.description, m.duration, m.release_date
            FROM viewing_history vh
            JOIN movies m ON vh.movie_id = m.id
            WHERE vh.user_id = ?;
            """;
}
