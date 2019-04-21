package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.UserProfile;

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
    userProfile.setMobilePhone("12345678910");
    userProfile.setCurrentJob("Java Developer");
    userProfile.setPlaceOfResidence("Berlin");
    userProfile.setProfileImage("profile photo");
    userProfile.setUserId(UserTestUtil.USER_ID.toString());

    return userProfile;
  }

  /*---------------UserProfileRequestDto----------------*/


  /*-------------------UserProfileDto-------------------*/
}
