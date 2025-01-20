package org.senla.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.util.Date;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieInfoDto {
    private Long id;
    @NotBlank(message = "Название фильма не должно быть пустым")
    private String title;

    private String description;

    private Duration duration;

    private Date releaseDate;

    private List<ReviewDto> reviews;

    private List<GenreDto> genres;

    private List<MemberDto> members;
}
