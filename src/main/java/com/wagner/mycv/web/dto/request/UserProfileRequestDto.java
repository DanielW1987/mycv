package com.wagner.mycv.web.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserProfileRequestDto {

  @NotNull
  @NotBlank
  private String firstName;

  private String lastName;
  private String currentJob;
  private String placeOfResidence;

  @Email
  private String email;
  private String mobilePhone;
  private String profileImage;

  @NotNull
  @NotBlank
  private String userId;

}
