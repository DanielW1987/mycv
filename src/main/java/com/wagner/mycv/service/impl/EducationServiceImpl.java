package com.wagner.mycv.service.impl;

import com.wagner.mycv.config.ApplicationConstants;
import com.wagner.mycv.model.entity.Education;
import com.wagner.mycv.model.repository.EducationRepository;
import com.wagner.mycv.service.EducationService;
import com.wagner.mycv.web.dto.request.EducationRequestDto;
import com.wagner.mycv.web.dto.EducationDto;
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
public class EducationServiceImpl implements EducationService {

  private final EducationRepository educationRepository;
  private final ModelMapper         modelMapper;

  @Autowired
  public EducationServiceImpl(EducationRepository educationRepository) {
    this.educationRepository = educationRepository;
    this.modelMapper         = new ModelMapper();
  }

  @NotNull
  @Override
  public List<EducationDto> findAll() {
    Sort sort = new Sort(Sort.Direction.DESC, "end");

    return educationRepository.findAll(sort)
            .stream()
            .map(education -> modelMapper.map(education, EducationDto.class))
            .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Optional<EducationDto> find(long id) {
    Optional<Education> education = educationRepository.findById(id);

    return education.map(value -> modelMapper.map(value, EducationDto.class));
  }

  @NotNull
  @Override
  public EducationDto create(@NotNull EducationRequestDto request) {
    Education education = modelMapper.map(request, Education.class);
    education.setUserId(ApplicationConstants.PUBLIC_USER_ID);
    educationRepository.save(education);

    return modelMapper.map(education, EducationDto.class);
  }

  @NotNull
  @Override
  public List<EducationDto> createAll(@NotNull Iterable<EducationRequestDto> request) {
    List<Education> educations = new ArrayList<>();
    request.forEach(educationRequestDto -> {
      Education education = modelMapper.map(educationRequestDto, Education.class);
      education.setUserId(ApplicationConstants.PUBLIC_USER_ID);
      educations.add(education);
    });

    educationRepository.saveAll(educations);

    return educations.stream()
            .map(education -> modelMapper.map(education, EducationDto.class))
            .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Optional<EducationDto> update(long id, @NotNull EducationRequestDto request) {
    Optional<Education> educationOptional = educationRepository.findById(id);
    EducationDto educationResponse        = null;

    if (educationOptional.isPresent()) {
      Education education = educationOptional.get();
      modelMapper.map(request, education);

      educationRepository.save(education);
      educationResponse = modelMapper.map(education, EducationDto.class);
    }

    return Optional.ofNullable(educationResponse);
  }

  @Override
  public boolean delete(long id) {
    if (educationRepository.existsById(id)) {
      educationRepository.deleteById(id);
      return true;
    }

    // entity doesn't exist
    return false;
  }
}
