package com.wagner.mycv.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@Builder
public class LanguageRequestDto {

  @NotNull
  @NotBlank
  private String name;

  @Min(value = 0)
  @Max(value = 100)
  private byte level;

  @NotNull
  @NotBlank
  private String userId;

}
