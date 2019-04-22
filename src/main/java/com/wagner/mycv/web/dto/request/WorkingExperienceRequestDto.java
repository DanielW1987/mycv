package com.wagner.mycv.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class WorkingExperienceRequestDto {

  @NotNull
  @NotBlank
  private String company;

  @NotNull
  private LocalDate begin;

  private LocalDate end;

  @NotNull
  @NotBlank
  private String jobTitle;

  @NotNull
  @NotBlank
  private String placeOfWork;

  private List<String> focalPoints;

  @NotNull
  @NotBlank
  private String userId;

}
