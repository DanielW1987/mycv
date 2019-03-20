package com.wagner.mycv.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LanguageDto extends AbstractDto {

  private long   id;
  private String name;
  private byte   level;
}
