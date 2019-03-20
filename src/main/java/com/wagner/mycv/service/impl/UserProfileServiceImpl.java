package com.wagner.mycv.service.impl;

import com.wagner.mycv.model.entity.UserProfile;
import com.wagner.mycv.model.repository.UserProfileRepository;
import com.wagner.mycv.service.UserProfileService;
import com.wagner.mycv.web.dto.request.UserProfileRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

  private final UserProfileRepository userProfileRepository;
  private final ModelMapper           modelMapper;

  @Autowired
  public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
    this.userProfileRepository = userProfileRepository;
    this.modelMapper           = new ModelMapper();
  }

  @Override
  public void create(UserProfileRequestDto request) {
    UserProfile userProfile = modelMapper.map(request, UserProfile.class);
    userProfileRepository.save(userProfile);
  }
}
