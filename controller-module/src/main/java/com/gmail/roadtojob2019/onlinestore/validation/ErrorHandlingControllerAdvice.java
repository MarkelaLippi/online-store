package com.gmail.roadtojob2019.onlinestore.validation;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String onBindException(final BindException e, final Model model) {
        ValidationErrorResponse errors = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.getViolations().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        model.addAttribute("errors", errors.getViolations());
        return "error";
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String onMissingServletRequestParameterException(final MissingServletRequestParameterException e, final Model model) {
        ValidationErrorResponse errors = new ValidationErrorResponse();
        errors.getViolations().add(new Violation(e.getParameterName(), e.getMessage()));
        model.addAttribute("errors", errors.getViolations());
        return "error";
    }
}
