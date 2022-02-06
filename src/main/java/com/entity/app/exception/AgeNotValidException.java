package com.entity.app.exception;

/**
 * @author Igor Baiborodine
 */
public class AgeNotValidException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public AgeNotValidException(String message) {
    super(message);
  }
}
