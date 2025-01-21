package org.senla.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationErrors(
            MethodArgumentNotValidException ex,
            Model model,
            HttpServletRequest request
    ) {
        model.addAttribute("validationErrors", ex.getBindingResult().getFieldErrors()); // Изменено на "validationErrors"

        String referer = request.getHeader("referer");

        if (referer == null || referer.isEmpty()) {
            return "error";
        }

        return "redirect:" + referer;
    }
}
