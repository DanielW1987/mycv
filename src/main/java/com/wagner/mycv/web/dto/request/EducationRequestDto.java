package com.wagner.mycv.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class EducationRequestDto {

  @NotBlank
  private String facility;

  @NotNull
  private LocalDate begin;

  @NotNull
  private LocalDate end;

  @NotBlank
  private String graduation;

  public Map<String, String> toMap() {
    Map<String, String> map = new HashMap<>();
    map.put("facility", facility);
    map.put("begin", begin.toString());
    map.put("end", end.toString());
    map.put("graduation", graduation);

    return map;
  }
}
