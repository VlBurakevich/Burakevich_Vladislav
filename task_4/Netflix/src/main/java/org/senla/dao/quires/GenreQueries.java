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

    public static final String INSERT_WITHOUT_PARENT_ID = """
            INSERT INTO genres (name, description)
            VALUES (?, ?);
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

    public static final String GET_ALL_BY_MOVIE_ID = """
            SELECT g.id, g.parent_genre_id, g.name, g.description
            FROM genres g
            JOIN movie_genres mg ON g.id = mg.genre_id
            WHERE mg.movie_id = ?;
            """;

    public static final String GET_ID_BY_NAME = """
            "SELECT id
            FROM genres
            WHERE name = ?"
            """;

    public static final String INSERT_MOVIE_GENRE_LINK = """
            INSERT INTO movie_genres (movie_id, genre_id)
            VALUES (?, ?)
            """;
}
