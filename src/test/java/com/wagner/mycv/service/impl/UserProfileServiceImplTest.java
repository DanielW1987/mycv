package com.wagner.mycv.service.impl;

import com.wagner.mycv.model.entity.UserProfile;
import com.wagner.mycv.model.repository.UserProfileRepository;
import com.wagner.mycv.testutil.UserProfileTestUtil;
import com.wagner.mycv.web.dto.request.UserProfileRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserProfileServiceImplTest {

  private UserProfile userProfileEntity;
  private UserProfileRequestDto userProfileRequestDto;

  @InjectMocks
  private UserProfileServiceImpl userProfileService;

  @Mock
  private UserProfileRepository userProfileRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    userProfileEntity     = UserProfileTestUtil.createTestEntity();
    userProfileRequestDto = UserProfileTestUtil.createUserProfileRequestDto();
  }

  @Test
  void test_create() {
    // given
    when(userProfileRepository.save(any(UserProfile.class))).thenReturn(userProfileEntity);

    // when
    userProfileService.create(userProfileRequestDto);

    // then
    verify(userProfileRepository, times(1)).save(any(UserProfile.class));
  }
}