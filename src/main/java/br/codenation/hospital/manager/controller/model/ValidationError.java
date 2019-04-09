package br.codenation.hospital.manager.controller.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends ResponseError {
  List<ValidationMessage> errors = new ArrayList<>();

  public ValidationError(Throwable throwable) {
    super(throwable);
  }

  public void addError(String fieldName, String message) {
    errors.add(new ValidationMessage(fieldName, message));
  }
}
