package com.wagner.mycv.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProgrammingProjectDto extends AbstractEntityDto {

  private String       name;
  private String       description;
  private List<String> technologiesUsed;
  private String       vcsUrl;

}
