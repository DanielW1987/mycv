package com.wagner.mycv.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LanguageDto extends AbstractEntityDto {

  private String name;
  private byte   level;
}
