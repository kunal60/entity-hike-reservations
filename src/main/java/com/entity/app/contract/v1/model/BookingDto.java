package com.entity.app.contract.v1.model;

import com.entity.app.model.validator.BookingAllowedEventDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Generated;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@BookingAllowedEventDate
@JsonIgnoreProperties(ignoreUnknown = true)
@Generated
public class BookingDto {

  @NotEmpty
  @Size(max = 50)
  private String firstName;

  @NotEmpty
  @Size(max = 50)
  private String lastName;

  @NotEmpty
  @Size(max = 50)
  private String email;

  @NotNull
  @Future(message = "Event date must be a future date")
  private LocalDate eventDate;

  @NotNull
  private Integer age;

  private boolean active;

}