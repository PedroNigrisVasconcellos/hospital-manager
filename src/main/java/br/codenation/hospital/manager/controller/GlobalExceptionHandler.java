package br.codenation.hospital.manager.controller;

import br.codenation.hospital.manager.controller.model.ResponseError;
import br.codenation.hospital.manager.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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


}
