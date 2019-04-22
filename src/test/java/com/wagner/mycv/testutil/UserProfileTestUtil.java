package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.UserProfile;
import com.wagner.mycv.web.dto.request.UserProfileRequestDto;

public class UserProfileTestUtil {

  private UserProfileTestUtil() {
    // has only static methods
  }

  /*--------------------UserProfile---------------------*/
  public static UserProfile createTestEntity() {
    return createTestEntity("John", "Doe");
  }

  public static UserProfile createTestEntity(String firstName, String lastName) {
    UserProfile userProfile = new UserProfile();
    userProfile.setFirstName(firstName);
    userProfile.setLastName(lastName);
    userProfile.setEmail("john.doe@example.com");
    userProfile.setMobilePhone("01520 12 34 567");
    userProfile.setCurrentJob("Java Developer");
    userProfile.setPlaceOfResidence("Berlin");
    userProfile.setProfileImage("profile photo");
    userProfile.setUserId(UserTestUtil.USER_ID.toString());

    return userProfile;
  }

  /*---------------UserProfileRequestDto----------------*/
  public static UserProfileRequestDto createUserProfileRequestDto() {
    return UserProfileRequestDto.builder()
            .firstName("John")
            .lastName("Doe")
            .currentJob("Java Developer")
            .email("john.doe@example")
            .mobilePhone("01520 12 34 567")
            .placeOfResidence("Berlin")
            .profileImage("Profile Image")
            .userId(UserTestUtil.USER_ID.toString())
            .build();
  }

  /*-------------------UserProfileDto-------------------*/
}
