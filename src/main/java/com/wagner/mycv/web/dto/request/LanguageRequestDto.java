package com.wagner.mycv.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class LanguageRequestDto {

  @NotBlank
  private String name;

  @Min(value = 0)
  @Max(value = 100)
  private byte level;

  @NotBlank
  private String userId;

  public Map<String, String> toMap() {
    Map<String, String> map = new HashMap<>();
    map.put("name", name);
    map.put("level", String.valueOf(level));
    map.put("userId", userId);

    return map;
  }

}
