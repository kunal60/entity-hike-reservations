package com.entity.app.exception;

/**
 * @author Igor Baiborodine
 */
public class TrailNotAvailableException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public TrailNotAvailableException(String message) {
    super(message);
  }
}
