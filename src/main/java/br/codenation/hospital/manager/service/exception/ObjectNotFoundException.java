package br.codenation.hospital.manager.service.exception;

public class ObjectNotFoundException extends RuntimeException {

  public ObjectNotFoundException() {}

  public ObjectNotFoundException(String s) {
    super(s);
  }

  public ObjectNotFoundException(String s, Throwable throwable) {
    super(s, throwable);
  }

  public ObjectNotFoundException(Throwable throwable) {
    super(throwable);
  }
}
