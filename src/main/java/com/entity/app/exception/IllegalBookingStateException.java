package com.entity.app.exception;

/**
 * @author Igor Baiborodine
 */
public class IllegalBookingStateException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public IllegalBookingStateException(String message) {
    super(message);
  }
}
