package org.senla.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponseDto {
    private String message;

    @Builder.Default
    private String timestamp = LocalDateTime.now().toString();
}
