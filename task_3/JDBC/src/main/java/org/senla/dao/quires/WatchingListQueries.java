package org.senla.dao.quires;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WatchingListQueries {
    public static final String GET_BY_ID = """
            SELECT user_id, movie_id, watched_at
            FROM watching_list
            WHERE user_id = ? AND movie_id = ?;
            """;

    public static final String GET_ALL = """
            SELECT user_id, movie_id, watched_at
            FROM watching_list;
            """;

    public static final String INSERT = """
            INSERT INTO watching_history (user_id, movie_id, watched_at)
            VALUES (?, ?, ?);
            """;

    public static final String UPDATE = """
            UPDATE watching_history
            SET watched_at = ?
            WHERE user_id = ? AND movie_id = ?;
            """;

    public static final String DELETE = """
            DELETE FROM watching_history
            WHERE user_id = ? AND movie_id = ?;
            """;


}
