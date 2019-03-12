package br.codenation.hospital.manager.controller.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.CompletionException;

public class ResponseError {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  private static final String DEFAULT_MESSAGE = "No message available";
  private static final DateFormat DATE_FORMAT = new StdDateFormat();

  private Throwable throwable;
  private final String message;
  private final String timestamp;

  public ResponseError(Throwable throwable) {
    setThrowable(throwable);
    String localizedMessage = this.throwable.getLocalizedMessage();
    this.message = StringUtils.isEmpty(localizedMessage) ? DEFAULT_MESSAGE : localizedMessage;
    this.timestamp = DATE_FORMAT.format(new Date());
  }

  private void setThrowable(Throwable throwable) {
    this.throwable = throwable;
    if (throwable instanceof CompletionException) {
      this.throwable = throwable.getCause();
    }
  }

  public String getMessage() {
    return message;
  }

  public String getTimestamp() {
    return timestamp;
  }

  @Override
  public String toString() {
    final ObjectNode rootNode = MAPPER.createObjectNode();
    rootNode.put("message", message);
    rootNode.put("timestamp", timestamp);
    return rootNode.toString();
  }
}
