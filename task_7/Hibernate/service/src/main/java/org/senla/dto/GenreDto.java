package org.senla.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.senla.entity.Genre;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {
    private Long id;
    private String name;
    private String description;

    public GenreDto(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
        this.description = genre.getDescription();
    }
}
