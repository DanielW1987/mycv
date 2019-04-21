package com.wagner.mycv.service.impl;

import com.wagner.mycv.model.entity.ProgrammingProject;
import com.wagner.mycv.model.repository.ProgrammingProjectRepository;
import com.wagner.mycv.service.ProgrammingProjectService;
import com.wagner.mycv.web.dto.request.ProgrammingProjectRequestDto;
import com.wagner.mycv.web.dto.ProgrammingProjectDto;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgrammingProjectServiceImpl implements ProgrammingProjectService {

  private final ProgrammingProjectRepository programmingProjectRepository;
  private final ModelMapper              modelMapper;

  @Autowired
  public ProgrammingProjectServiceImpl(ProgrammingProjectRepository programmingProjectRepository) {
    this.programmingProjectRepository = programmingProjectRepository;
    this.modelMapper              = new ModelMapper();
  }

  @NotNull
  @Override
  public List<ProgrammingProjectDto> findAll() {
    return programmingProjectRepository.findAll()
            .stream()
            .map(programmingProject -> modelMapper.map(programmingProject, ProgrammingProjectDto.class))
            .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Optional<ProgrammingProjectDto> find(long id) {
    Optional<ProgrammingProject> privateProject = programmingProjectRepository.findById(id);

    return privateProject.map(value -> modelMapper.map(value, ProgrammingProjectDto.class));
  }

  @NotNull
  @Override
  public ProgrammingProjectDto create(@NotNull ProgrammingProjectRequestDto request) {
    ProgrammingProject programmingProject = modelMapper.map(request, ProgrammingProject.class);

    programmingProjectRepository.save(programmingProject);

    return modelMapper.map(programmingProject, ProgrammingProjectDto.class);
  }

  @NotNull
  @Override
  public List<ProgrammingProjectDto> createAll(@NotNull Iterable<ProgrammingProjectRequestDto> request) {
    List<ProgrammingProject> programmingProjects = new ArrayList<>();
    request.forEach(programmingProjectRequestDto -> programmingProjects.add(modelMapper.map(programmingProjectRequestDto, ProgrammingProject.class)));

    programmingProjectRepository.saveAll(programmingProjects);

    return programmingProjects.stream()
            .map(programmingProject -> modelMapper.map(programmingProject, ProgrammingProjectDto.class))
            .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Optional<ProgrammingProjectDto> update(long id, @NotNull ProgrammingProjectRequestDto request) {
    Optional<ProgrammingProject> privateProjectOptional = programmingProjectRepository.findById(id);
    ProgrammingProjectDto privateProjectResponse        = null;

    if (privateProjectOptional.isPresent()) {
      ProgrammingProject programmingProject = privateProjectOptional.get();
      modelMapper.map(request, programmingProject);

      programmingProjectRepository.save(programmingProject);
      privateProjectResponse = modelMapper.map(programmingProject, ProgrammingProjectDto.class);
    }

    return Optional.ofNullable(privateProjectResponse);
  }

  @Override
  public boolean delete(long id) {
    if (programmingProjectRepository.existsById(id)) {
      programmingProjectRepository.deleteById(id);
      return true;
    }

    // entity doesn't exist
    return false;
  }

}
