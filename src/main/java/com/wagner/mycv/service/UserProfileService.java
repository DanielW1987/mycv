package com.wagner.mycv.service;

import com.wagner.mycv.web.dto.request.UserProfileRequestDto;

public interface UserProfileService {

  void create(UserProfileRequestDto request);

}
