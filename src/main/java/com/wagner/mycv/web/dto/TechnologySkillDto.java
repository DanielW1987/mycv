package com.wagner.mycv.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TechnologySkillDto extends AbstractEntityDto {

  private String       category;
  private List<String> skillNames;

}
