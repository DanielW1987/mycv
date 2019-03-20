package com.wagner.mycv.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class WorkingExperienceDto extends AbstractDto {

  private long         id;
  private String       company;
  private LocalDate    begin;
  private LocalDate    end;
  private String       jobTitle;
  private String       placeOfWork;
  private List<String> focalPoints;

}
