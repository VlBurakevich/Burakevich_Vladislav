package org.senla.dao.quires;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ReviewQueries {
    public static final String GET_BY_ID = """
            SELECT id, user_id, movie_id, rating, comment
            FROM reviews
            WHERE id = ?;
            """;

    public static final String GET_ALL = """
            SELECT id, user_id, movie_id, rating, comment
            FROM reviews;
            """;

    public static final String INSERT = """
            INSERT INTO reviews (user_id, movie_id, rating, comment)
            VALUES  (?, ?, ?, ?);
            """;

    public static final String UPDATE = """
            UPDATE reviews
            SET user_id = ?, movie_id = ?, rating = ?, comment = ?
            WHERE id = ?;
            """;

    public static final String DELETE = """
            DELETE FROM reviews
            WHERE id = ?;
            """;

}
