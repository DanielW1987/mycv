package com.wagner.mycv.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class TechnologySkillRequestDto {

  @NotBlank
  private String category;

  @NotNull
  private List<String> skillNames;

  public Map<String, Object> toMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("category", category);
    map.put("skillNames", skillNames);

    return map;
  }

}
