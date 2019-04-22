package com.wagner.mycv.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class EducationRequestDto {

  @NotNull
  @NotBlank
  private String facility;

  @NotNull
  private LocalDate begin;

  @NotNull
  private LocalDate end;

  @NotNull
  @NotBlank
  private String graduation;

  @NotNull
  @NotBlank
  private String userId;
}
