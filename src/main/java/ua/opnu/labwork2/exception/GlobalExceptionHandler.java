package ua.opnu.labwork2.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ua.opnu.labwork2.dto.ApiErrorResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        String path = request.getDescription(false).replace("uri=", "");

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation failed for one or more fields",
                path,
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            BadRequestException.class
    })
    public ResponseEntity<ApiErrorResponse> handleBadRequestGroup(
            Exception ex,
            WebRequest request
    ) {
        String customMessage = "The request contains invalid data or malformed structure";
        if (ex instanceof BadRequestException) {
            customMessage = ex.getMessage();
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            customMessage = "Parameter type mismatch: " + ex.getMessage();
        }

        return createErrorResponse(HttpStatus.BAD_REQUEST, customMessage, request);
    }

    @ExceptionHandler({
            ResourceNotFoundException.class,
            NoSuchElementException.class
    })
    public ResponseEntity<ApiErrorResponse> handleNotFoundGroup(
            Exception ex,
            WebRequest request
    ) {
        return createErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler({
            DuplicateResourceException.class,
            ConflictOperationException.class,
            DataIntegrityViolationException.class
    })
    public ResponseEntity<ApiErrorResponse> handleConflictGroup(
            Exception ex,
            WebRequest request
    ) {
        String message = ex.getMessage();
        if (ex instanceof DataIntegrityViolationException) {
            message = "Database integrity constraint violation: Duplicate key or foreign key conflict.";
        }
        return createErrorResponse(HttpStatus.CONFLICT, message, request);
    }

    @ExceptionHandler({
            DatabaseOperationException.class,
            RuntimeException.class,
            Exception.class
    })
    public ResponseEntity<ApiErrorResponse> handleInternalServerErrorGroup(
            Exception ex,
            WebRequest request
    ) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    private ResponseEntity<ApiErrorResponse> createErrorResponse(
            HttpStatus status,
            String message,
            WebRequest request
    ) {
        String path = request.getDescription(false).replace("uri=", "");

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path,
                null // Для звичайних винятків (не валідації полів) мапа errors дорівнює null
        );

        return ResponseEntity.status(status).body(errorResponse);
    }
}
