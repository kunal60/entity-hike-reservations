package com.entity.app.model.validator;

import com.entity.app.contract.v1.model.BookingDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class BookingAllowedStartDateValidator
    implements ConstraintValidator<BookingAllowedEventDate, BookingDto> {

  @Override
  public void initialize(BookingAllowedEventDate constraintAnnotation) {
    // no additional initialization needed
  }

  @Override
  public boolean isValid(BookingDto booking, ConstraintValidatorContext constraintValidatorContext) {
    return LocalDate.now().isBefore(booking.getEventDate())
        && booking.getEventDate().isBefore(LocalDate.now().plusMonths(1).plusDays(1));
  }
}
