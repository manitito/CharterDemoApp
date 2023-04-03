package com.charter.charterdemoapp.excpetion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final ObjectMapper mapper;
    private ObjectWriter objectWriter;

    @PostConstruct
    private void init() {
        objectWriter = mapper.writerWithDefaultPrettyPrinter();
    }

    @SneakyThrows
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        log.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(objectWriter.writeValueAsString(problemDetail));
    }
}