package org.senla.mapper;

import lombok.experimental.UtilityClass;
import org.senla.dto.GenreDto;
import org.senla.entity.Genre;

@UtilityClass
public class GenreMapper {
    public static GenreDto toDto(Genre genre) {
        if (genre == null) {
            return null;
        }
        return new GenreDto(
                genre.getId(),
                genre.getName(),
                genre.getDescription()
        );
    }

    public static Genre toEntity(GenreDto genreDto) {
        if (genreDto == null) {
            return null;
        }
        Genre genre = new Genre();
        genre.setId(genreDto.getId());
        genre.setName(genreDto.getName());
        genre.setDescription(genreDto.getDescription());
        return genre;
    }
}
