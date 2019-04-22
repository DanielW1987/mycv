package com.wagner.mycv.web.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class UserProfileRequestDto {

  @NotBlank
  private String firstName;

  private String lastName;
  private String currentJob;
  private String placeOfResidence;

  @Email
  private String email;
  private String mobilePhone;
  private String profileImage;

  @NotBlank
  private String userId;

  public Map<String, String> toMap() {
    Map<String, String> map = new HashMap<>();
    map.put("firstName", firstName);
    map.put("lastName", lastName);
    map.put("currentJob", currentJob);
    map.put("placeOfResidence", placeOfResidence);
    map.put("email", email);
    map.put("mobilePhone", mobilePhone);
    map.put("profileImage", profileImage);
    map.put("userId", userId);

    return map;
  }

}
