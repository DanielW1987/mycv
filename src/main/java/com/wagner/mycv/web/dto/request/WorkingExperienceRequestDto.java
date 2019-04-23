package com.wagner.mycv.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class WorkingExperienceRequestDto {

  @NotBlank
  private String company;

  @NotNull
  private LocalDate begin;

  private LocalDate end;

  @NotBlank
  private String jobTitle;

  @NotBlank
  private String placeOfWork;

  private List<String> focalPoints;

  public Map<String, Object> toMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("company", company);
    map.put("begin", begin);
    map.put("end", end);
    map.put("jobTitle", jobTitle);
    map.put("placeOfWork", placeOfWork);
    map.put("focalPoints", focalPoints);

    return map;
  }

}
