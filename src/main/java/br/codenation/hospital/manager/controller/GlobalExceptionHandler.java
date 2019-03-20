package br.codenation.hospital.manager.controller;

import br.codenation.hospital.manager.controller.model.ResponseError;
import br.codenation.hospital.manager.service.exception.ObjectNotFoundException;
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

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(RuntimeException.class)
  public ResponseError handleInternalServerError(Throwable throwable) {
    LOGGER.error("Exception - General (500).", throwable);
    return new ResponseError(throwable);
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ObjectNotFoundException.class)
  public ResponseError handleObjectNotFoundException(Throwable throwable) {
    LOGGER.error("Exception - ObjectNotFound (404).", throwable);
    return new ResponseError(throwable);
  }
}
