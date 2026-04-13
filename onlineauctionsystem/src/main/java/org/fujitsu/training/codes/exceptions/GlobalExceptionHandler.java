package org.fujitsu.training.codes.exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        logger.warn("Handled business exception: {}", ex.getMessage(), ex);
        model.addAttribute("errorTitle", "Invalid Request");
        model.addAttribute("errorMessage", ex.getMessage());
        return "errIllegalArgView";
    }

    @ExceptionHandler(NumberFormatException.class)
    public String handleNumberFormat(NumberFormatException ex, Model model) {
        logger.warn("Handled number format exception: {}", ex.getMessage(), ex);
        model.addAttribute("errorTitle", "Invalid Number");
        model.addAttribute("errorMessage", "One of the values provided was not a valid number.");
        return "numberFormatView";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception ex, Model model) {
        logger.error("Unhandled application exception: {}", ex.getMessage(), ex);
        model.addAttribute("errorTitle", "Something Went Wrong");
        model.addAttribute("errorMessage", "An unexpected error occurred. Please try again or contact support.");
        return "globalErrView";
    }
}
