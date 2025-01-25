package org.senla.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {
    private Long id;
    @NotBlank(message = "Genre name cannot be empty")
    private String name;
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
}
