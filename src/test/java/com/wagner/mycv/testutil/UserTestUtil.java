package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.User;

import java.util.UUID;

public class UserTestUtil {

  public static final UUID USER_ID = UUID.fromString("ebbddc3e-8414-4555-97ca-c247cc785cef");

  private UserTestUtil() {
    // has only static fields
  }

  /*--------------------User---------------------*/
  public static User createTestEntity() {
    return createTestEntity(UUID.randomUUID().toString());
  }

  public static User createTestEntity(String uuid) {
    User user = new User();
    user.setUserId(uuid);

    return user;
  }

  /*---------------UserRequestDto----------------*/


  /*-------------------UserDto-------------------*/
}
