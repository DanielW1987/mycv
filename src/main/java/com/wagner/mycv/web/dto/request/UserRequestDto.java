package com.wagner.mycv.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class UserRequestDto {

  @NotBlank
  private String userId;
}
