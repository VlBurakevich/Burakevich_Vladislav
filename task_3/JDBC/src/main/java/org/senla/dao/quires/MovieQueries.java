package org.senla.dao.quires;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MovieQueries {
    public static final String GET_BY_ID = """
            SELECT id, title, description, duration, release_date
            FROM movies
            WHERE id = ?;
            """;

    public static final String GET_ALL = """
            SELECT id, title, description, duration, release_date
            FROM movies;
            """;

    public static final String INSERT = """
            INSERT INTO movies (title, description, duration, release_date)
            VALUES (?, ?, ?, ?);
            """;

    public static final String UPDATE_PRIMARY_INFO_BY_ID = """
            UPDATE movies
            SET title = ?, description = ?, duration = ?, release_date = ?
            WHERE id = ?;
            """;

    public static final String DELETE_PRIMARY_INFO_BY_ID = """
            DELETE FROM movies
            WHERE id = ?;
            """;

}
