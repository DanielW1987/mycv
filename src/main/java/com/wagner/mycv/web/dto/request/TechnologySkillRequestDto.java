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

  @NotBlank
  private String category;

  @NotNull
  private List<String> skillNames;

  @NotBlank
  private String userId;

}
