package cc.sven.springboottemplate.exception;

import cc.sven.springboottemplate.dto.ValidationFailedDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ValidationFailedDto.builder()
                        .validatedObject(ex.getParameter().getParameterName())
                        .message(ex.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<List<Void>> handleException(@NonNull final Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(value = {HttpUnprocessableEntityException.class})
    public ResponseEntity<List<Void>> handleHttpUnprocessableEntityException(@NonNull final HttpUnprocessableEntityException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

}
