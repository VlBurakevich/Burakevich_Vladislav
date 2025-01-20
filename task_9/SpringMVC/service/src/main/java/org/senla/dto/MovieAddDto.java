package org.senla.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieAddDto {

    @NotBlank(message = "Название фильма не должно быть пустым")
    private String title;

    @NotBlank(message = "Описание фильма не должно быть пустым")
    private String description;

    @NotBlank(message = "Продолжительность фильмане может быть пустой")
    private String duration;

    @NotBlank(message = "Дата релиза не должна быть пустой")
    private String releaseDate;

    private String genreNames;

    private List<MemberDto> members;
}
