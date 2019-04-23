package com.wagner.mycv.service.impl;

import com.wagner.mycv.config.ApplicationConstants;
import com.wagner.mycv.model.entity.WorkingExperience;
import com.wagner.mycv.model.repository.WorkingExperienceRepository;
import com.wagner.mycv.service.WorkingExperienceService;
import com.wagner.mycv.web.dto.request.WorkingExperienceRequestDto;
import com.wagner.mycv.web.dto.WorkingExperienceDto;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkingExperienceServiceImpl implements WorkingExperienceService {

  private final WorkingExperienceRepository workingExperienceRepository;
  private final ModelMapper                 modelMapper;

  @Autowired
  public WorkingExperienceServiceImpl(WorkingExperienceRepository workingExperienceRepository) {
    this.workingExperienceRepository = workingExperienceRepository;
    this.modelMapper                 = new ModelMapper();
  }

  @NotNull
  @Override
  public List<WorkingExperienceDto> findAll() {
    Sort sort = new Sort(Sort.Direction.DESC, "end");

    return workingExperienceRepository.findAll(sort)
            .stream()
            .map(workingExperience -> modelMapper.map(workingExperience, WorkingExperienceDto.class))
            .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Optional<WorkingExperienceDto> find(long id) {
    Optional<WorkingExperience> workingExperience = workingExperienceRepository.findById(id);

    return workingExperience.map(value -> modelMapper.map(value, WorkingExperienceDto.class));
  }

  @NotNull
  @Override
  public WorkingExperienceDto create(@NotNull WorkingExperienceRequestDto request) {
    WorkingExperience workingExperience = modelMapper.map(request, WorkingExperience.class);
    workingExperience.setUserId(ApplicationConstants.PUBLIC_USER_ID);

    workingExperienceRepository.save(workingExperience);
    return modelMapper.map(workingExperience, WorkingExperienceDto.class);
  }

  @NotNull
  @Override
  public List<WorkingExperienceDto> createAll(@NotNull Iterable<WorkingExperienceRequestDto> request) {
    List<WorkingExperience> workingExperiences = new ArrayList<>();
    request.forEach(workingExperienceRequestDto -> {
      WorkingExperience workingExperience = modelMapper.map(workingExperienceRequestDto, WorkingExperience.class);
      workingExperience.setUserId(ApplicationConstants.PUBLIC_USER_ID);
      workingExperiences.add(workingExperience);
    });

    workingExperienceRepository.saveAll(workingExperiences);

    return workingExperiences.stream()
            .map(workingExperience -> modelMapper.map(workingExperience, WorkingExperienceDto.class))
            .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Optional<WorkingExperienceDto> update(long id, @NotNull WorkingExperienceRequestDto request) {
    Optional<WorkingExperience> workingExperienceOptional = workingExperienceRepository.findById(id);
    WorkingExperienceDto workingExperienceResponse        = null;

    if (workingExperienceOptional.isPresent()) {
      WorkingExperience workingExperience = workingExperienceOptional.get();
      modelMapper.map(request, workingExperience);

      workingExperienceRepository.save(workingExperience);
      workingExperienceResponse = modelMapper.map(workingExperience, WorkingExperienceDto.class);
    }

    return Optional.ofNullable(workingExperienceResponse);
  }

  @Override
  public boolean delete(long id) {
    if (workingExperienceRepository.existsById(id)) {
      workingExperienceRepository.deleteById(id);
      return true;
    }

    // entity doesn't exist
    return false;
  }
}
