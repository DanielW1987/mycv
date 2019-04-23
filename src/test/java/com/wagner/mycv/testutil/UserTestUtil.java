package com.wagner.mycv.testutil;

import com.wagner.mycv.config.ApplicationConstants;
import com.wagner.mycv.model.entity.User;
import com.wagner.mycv.web.dto.request.UserRequestDto;

import java.util.UUID;

public class UserTestUtil {

  public static final String USER_ID = ApplicationConstants.PUBLIC_USER_ID;

  private UserTestUtil() {
    // has only static fields
  }

  /*--------------------User---------------------*/
  public static User createTestEntity() {
    return createTestEntity(USER_ID);
  }

  public static User createTestEntity(String uuid) {
    User user = new User();
    user.setUserId(uuid);

    return user;
  }

  /*---------------UserRequestDto----------------*/
  public static UserRequestDto createUserRequestDto() {
    return UserRequestDto.builder()
            .userId(USER_ID)
            .build();
  }

  /*-------------------UserDto-------------------*/
}
