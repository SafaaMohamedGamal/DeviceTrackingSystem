package com.iot.device_tracking_system.exception;

import com.iot.device_tracking_system.model.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);
    
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                HttpStatusCode status, WebRequest request) {
    Map<String, Object> body = new HashMap<>();
    
    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .toList();
    
    body.put("success", false);
    body.put("errors", errors);
    body.put("data", null);
    
    
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }
  
  @ExceptionHandler(DeviceAPIException.class)
  public ResponseEntity<Response> handleDeviceAPIException(DeviceAPIException ex, HttpServletRequest request){

		Response response = new Response(false, ex.getMessage(), "");
		HttpStatus status = HttpStatus.BAD_REQUEST;
		log.catching(ex);
		return new ResponseEntity<>(response, status);
		
  }
  
}