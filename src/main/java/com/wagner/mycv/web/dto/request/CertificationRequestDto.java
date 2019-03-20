package com.wagner.mycv.web.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class CertificationRequestDto {

  @NotNull
  @NotBlank
  private String name;

  @NotNull
  private LocalDate dateOfAchievement;

  private String certificate; // ToDo: should be a file

  @NotNull
  @NotBlank
  private String userId;

  public Map<String, String> toMap() {
    Map<String, String> map = new HashMap<>();
    map.put("name", name);
    map.put("dateOfAchievement", dateOfAchievement.toString());
    map.put("certificate", certificate);
    map.put("userId", userId);

    return map;
  }

}
