package com.entity.app.exception;

/**
 * @author Igor Baiborodine
 */
public class BookingInvalidDateException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public BookingInvalidDateException(String message) {
    super(message);
  }
}
