package com.wagner.mycv.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EducationDto extends AbstractEntityDto {

  private long      id;
  private String    facility;
  private LocalDate begin;
  private LocalDate end;
  private String    graduation;
}
