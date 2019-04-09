package br.codenation.hospital.manager.controller;

import br.codenation.hospital.manager.controller.model.ResponseError;
import br.codenation.hospital.manager.controller.model.ValidationError;
import br.codenation.hospital.manager.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseError handleIResourceNotFoundError(Throwable throwable) {
    LOGGER.error("Exception - ResourceNotFound (404).", throwable);
    return new ResponseError(throwable);
  }

  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ValidationError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    LOGGER.error("Exception - MethodArgumentNotValid (422).", e.getCause());
    ValidationError validationError = new ValidationError(new Throwable("validation error"));

    for (FieldError x : e.getBindingResult().getFieldErrors()) {
      validationError.addError(x.getField(), x.getDefaultMessage());
    }

    return validationError;
  }
}
