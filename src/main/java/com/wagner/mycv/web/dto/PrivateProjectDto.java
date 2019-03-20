package com.wagner.mycv.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PrivateProjectDto extends AbstractDto {

  private long         id;
  private String       name;
  private String       description;
  private List<String> technologiesUsed;
  private String       vcsURL;

}
