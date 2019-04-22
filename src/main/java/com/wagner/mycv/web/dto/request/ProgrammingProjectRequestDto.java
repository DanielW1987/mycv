package com.wagner.mycv.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
public class ProgrammingProjectRequestDto {

  @NotNull
  @NotBlank
  private String name;
  private String description;

  @NotNull
  private List<String> technologiesUsed;

  private String vcsUrl;

  @NotNull
  @NotBlank
  private String userId;

}
