package org.senla.dao.quires;

import lombok.experimental.UtilityClass;

@UtilityClass
public class GenreQueries {
    public static final String GET_BY_ID = """
            SELECT id, parent_genre_id, name, description
            FROM genre
            WHERE id = ?;
            """;

    public static final String GET_ALL = """
            SELECT id, parent_genre_id, name, description
            FROM genres;
            """;

    public static final String INSERT = """
            INSERT INTO genres (id, parent_genre_id, name, description)
            VALUES (?, ?, ?, ?);
            """;

    public static final String UPDATE_PRIMARY_INFO_BY_ID = """
            UPDATE genres
            SET  parent_genre_id = ?, name = ?, description = ?
            WHERE id = ?;
            """;

    public static final String DELETE_PRIMARY_INFO_BY_ID = """
            DELETE FROM genres
            WHERE id = ?;
            """;
}
