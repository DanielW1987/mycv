package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.User;
import com.wagner.mycv.web.dto.request.UserRequestDto;

import java.util.UUID;

public class UserTestUtil {

  public static final UUID USER_ID = UUID.fromString("ebbddc3e-8414-4555-97ca-c247cc785cef");

  private UserTestUtil() {
    // has only static fields
  }

  /*--------------------User---------------------*/
  public static User createTestEntity() {
    return createTestEntity(USER_ID.toString());
  }

  public static User createTestEntity(String uuid) {
    User user = new User();
    user.setUserId(uuid);

    return user;
  }

  /*---------------UserRequestDto----------------*/
  public static UserRequestDto createUserRequestDto() {
    return UserRequestDto.builder()
            .userId(USER_ID.toString())
            .build();
  }

  /*-------------------UserDto-------------------*/
}
