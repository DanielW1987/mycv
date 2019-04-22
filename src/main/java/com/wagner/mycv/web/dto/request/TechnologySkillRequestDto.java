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
public class TechnologySkillRequestDto {

  @NotNull
  @NotBlank
  private String category;

  @NotNull
  @NotBlank
  private List<String> skillNames;

  @NotNull
  @NotBlank
  private String userId;

}
