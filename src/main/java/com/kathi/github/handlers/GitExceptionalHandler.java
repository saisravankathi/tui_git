package com.kathi.github.handlers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GitExceptionalHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (body == null) {
            ObjectMapper om = new ObjectMapper();
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("status", String.valueOf(status.value()));
            responseMap.put("message", ex.getMessage());
            try {
                body = om.writeValueAsString(responseMap);responseMap=null;om=null;
            } catch (JsonProcessingException e) {
                body = ex.getMessage();
            }
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.status(status).headers(headers).body(body);
    }
}
