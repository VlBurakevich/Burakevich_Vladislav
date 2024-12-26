package org.senla.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieAddDto {
    private String title;
    private String description;
    private String duration;
    private String releaseDate;
    private String genreNames;

    private String[] memberFirstNames;
    private String[] memberLastNames;
    private String[] memberNationalities;
    private String[] memberTypes;
    private String[] memberGenders;
}
