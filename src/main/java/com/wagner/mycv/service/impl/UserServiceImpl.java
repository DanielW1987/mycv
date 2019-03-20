package com.wagner.mycv.service.impl;

import com.wagner.mycv.model.entity.User;
import com.wagner.mycv.model.repository.UserRepository;
import com.wagner.mycv.service.UserService;
import com.wagner.mycv.web.dto.request.UserRequestDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
    this.modelMapper    = new ModelMapper();
  }

  @Override
  public void create(UserRequestDto request) {
    User user = modelMapper.map(request, User.class);
    userRepository.save(user);
  }
}
