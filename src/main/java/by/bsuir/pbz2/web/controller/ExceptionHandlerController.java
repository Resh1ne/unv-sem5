package by.bsuir.pbz2.web.controller;

import by.bsuir.pbz2.service.exception.ResourceNotFoundException;
import by.bsuir.pbz2.service.exception.ValidationException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@SuppressWarnings("unused")
public class ExceptionHandlerController {
    @ExceptionHandler
    public String handleResourceNotFoundException(ResourceNotFoundException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("statusCode", 404);
        return "error";
    }

    @ExceptionHandler
    public String handleValidationException(ValidationException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("statusCode", 400);
        return "error";
    }

    @ExceptionHandler
    public String handleServerError(Exception ex, HttpServletResponse response, Model model) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        model.addAttribute("statusCode", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        model.addAttribute("errorMessage", "Internal Server Error");
        return "error";
    }
}