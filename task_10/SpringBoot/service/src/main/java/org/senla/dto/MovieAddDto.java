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

    @NotBlank(message = "Movie title cannot be empty")
    private String title;

    @NotBlank(message = "Movie description cannot be empty")
    private String description;

    @NotBlank(message = "Movie duration cannot be empty")
    private String duration;

    @NotBlank(message = "Release date cannot be empty")
    private String releaseDate;

    private String genreNames;

    private List<MemberDto> members;
}
