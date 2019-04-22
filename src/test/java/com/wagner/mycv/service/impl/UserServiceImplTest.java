package com.wagner.mycv.service.impl;

import com.wagner.mycv.model.entity.User;
import com.wagner.mycv.model.repository.UserRepository;
import com.wagner.mycv.testutil.UserTestUtil;
import com.wagner.mycv.web.dto.request.UserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

  private User userEntity;
  private UserRequestDto userRequestDto;

  @InjectMocks
  private UserServiceImpl userService;

  @Mock
  private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    userEntity = UserTestUtil.createTestEntity();
    userRequestDto = UserTestUtil.createUserRequestDto();
  }

  @Test
  void test_create() {
    // given
    when(userRepository.save(any(User.class))).thenReturn(userEntity);

    // when
    userService.create(userRequestDto);

    // then
    verify(userRepository, times(1)).save(any(User.class));
  }
}