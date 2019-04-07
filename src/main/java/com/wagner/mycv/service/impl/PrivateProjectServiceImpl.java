package com.wagner.mycv.service.impl;

import com.wagner.mycv.model.entity.PrivateProject;
import com.wagner.mycv.model.repository.PrivateProjectRepository;
import com.wagner.mycv.service.PrivateProjectService;
import com.wagner.mycv.web.dto.request.PrivateProjectRequestDto;
import com.wagner.mycv.web.dto.PrivateProjectDto;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrivateProjectServiceImpl implements PrivateProjectService {

  private final PrivateProjectRepository privateProjectRepository;
  private final ModelMapper              modelMapper;

  @Autowired
  public PrivateProjectServiceImpl(PrivateProjectRepository privateProjectRepository) {
    this.privateProjectRepository = privateProjectRepository;
    this.modelMapper              = new ModelMapper();
  }

  @NotNull
  @Override
  public List<PrivateProjectDto> findAll() {
    return privateProjectRepository.findAll()
            .stream()
            .map(privateProject -> modelMapper.map(privateProject, PrivateProjectDto.class))
            .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Optional<PrivateProjectDto> find(long id) {
    Optional<PrivateProject> privateProject = privateProjectRepository.findById(id);

    return privateProject.map(value -> modelMapper.map(value, PrivateProjectDto.class));
  }

  @NotNull
  @Override
  public PrivateProjectDto create(@NotNull PrivateProjectRequestDto request) {
    PrivateProject privateProject = modelMapper.map(request, PrivateProject.class);

    privateProjectRepository.save(privateProject);

    return modelMapper.map(privateProject, PrivateProjectDto.class);
  }

  @NotNull
  @Override
  public List<PrivateProjectDto> createAll(@NotNull Iterable<PrivateProjectRequestDto> request) {
    List<PrivateProject> privateProjects = new ArrayList<>();
    request.forEach(privateProjectRequestDto -> privateProjects.add(modelMapper.map(privateProjectRequestDto, PrivateProject.class)));

    privateProjectRepository.saveAll(privateProjects);

    return privateProjects.stream()
            .map(privateProject -> modelMapper.map(privateProject, PrivateProjectDto.class))
            .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Optional<PrivateProjectDto> update(long id, @NotNull PrivateProjectRequestDto request) {
    Optional<PrivateProject> privateProjectOptional = privateProjectRepository.findById(id);
    PrivateProjectDto privateProjectResponse        = null;

    if (privateProjectOptional.isPresent()) {
      PrivateProject privateProject = privateProjectOptional.get();
      modelMapper.map(request, privateProject);

      privateProjectRepository.save(privateProject);
      privateProjectResponse = modelMapper.map(privateProject, PrivateProjectDto.class);
    }

    return Optional.ofNullable(privateProjectResponse);
  }

  @Override
  public boolean delete(long id) {
    if (privateProjectRepository.existsById(id)) {
      privateProjectRepository.deleteById(id);
      return true;
    }

    // entity doesn't exist
    return false;
  }

//  private PrivateProject mapRequestDto(PrivateProjectRequestDto request) {
//    PrivateProject privateProject = modelMapper.map(request, PrivateProject.class);
//    privateProject.setTechnologiesUsed(String.join(", ", request.getTechnologiesUsed()));
//
//    return privateProject;
//  }
}
