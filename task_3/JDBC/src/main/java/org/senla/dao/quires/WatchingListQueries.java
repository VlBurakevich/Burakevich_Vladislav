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
            INSERT INTO watching_list (user_id, movie_id, watched_at)
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

}
