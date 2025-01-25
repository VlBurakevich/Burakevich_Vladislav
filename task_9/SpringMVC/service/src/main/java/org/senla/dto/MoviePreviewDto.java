package org.senla.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MoviePreviewDto {
    private Long id;
    @NotBlank(message = "Title cannot be blank")
    private String title;
}
