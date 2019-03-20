package com.wagner.mycv.web.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class PrivateProjectRequestDto {

  @NotNull
  @NotBlank
  private String name;
  private String description;

  @NotNull
  private List<String> technologiesUsed;

  private String vcsURL;

  @NotNull
  @NotBlank
  private String userId;

}
