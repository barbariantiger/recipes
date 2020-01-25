package nl.maikel.exception;

import nl.maikel.utils.Message;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RecipeExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintValidationHandler(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<?> inexistentIdHandler(Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, Message.NOT_FOUND.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }
}
