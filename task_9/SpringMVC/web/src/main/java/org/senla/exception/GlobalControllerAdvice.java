package org.senla.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalControllerAdvice {

    private void addValidationErrorsToModel(BindingResult bindingResult, Model model) {
        if (bindingResult != null && bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                fieldErrors.put(error.getField(), error.getDefaultMessage());
            }

            model.addAttribute("fieldErrors", fieldErrors);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(
            IllegalArgumentException ex,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest request
    ) {
        addValidationErrorsToModel(bindingResult, model);
        model.addAttribute("error", ex.getMessage());

        return request.getHeader("Referer") != null ? "redirect:" + request.getHeader("Referer") : "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(
            Exception ex,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest request
    ) {
        addValidationErrorsToModel(bindingResult, model);
        model.addAttribute("error", "An unexpected error occurred: " + ex.getMessage());

        return request.getHeader("Referer") != null ? "redirect:" + request.getHeader("Referer") : "error";
    }

}
