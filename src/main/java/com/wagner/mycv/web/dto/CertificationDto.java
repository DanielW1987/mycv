package com.wagner.mycv.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CertificationDto extends AbstractEntityDto {

  private long      id;
  private String    name;
  private LocalDate dateOfAchievement;
  private String    certificate; // ToDo: should be a file

}
