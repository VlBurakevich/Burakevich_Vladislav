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
    @NotBlank(message = "Название жанра не должно быть пустым")
    private String name;
    @Size(max = 255, message = "Описание должно быть не длиннее 255 символов")
    private String description;
}
