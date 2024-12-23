package org.senla.dao.quires;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WatchingListQueries {
    public static final String GET_BY_ID = """
            SELECT id, user_id, movie_id, watched_at
            FROM watching_list
            WHERE id = ?;
            """;

    public static final String GET_ALL = """
            SELECT id, user_id, movie_id, watched_at
            FROM watching_list;
            """;

    public static final String INSERT = """
            INSERT INTO watching_list (user_id, movie_id, added_at)
            VALUES (?, ?, ?);
            """;

    public static final String UPDATE_PRIMARY_INFO_BY_ID = """
            UPDATE watching_list
            SET user_id = ?, movie_id = ?, watched_at = ?
            WHERE id = ?;
            """;

    public static final String DELETE_PRIMARY_INFO_BY_ID = """
            DELETE FROM watching_list
            WHERE id = ?;
            """;

    public static final String DELETE_BY_USER_AND_MOVIE = """
            DELETE FROM watching_list
            WHERE user_id = ? AND movie_id = ?;
            """;
    public static final String GET_MOVIES_BY_USER_ID = """
            SELECT m.id, m.title, m.description, m.duration, m.release_date
            FROM watching_list wl
            JOIN movies m ON wl.movie_id = m.id
            WHERE wl.user_id = ?;
            """;
}
