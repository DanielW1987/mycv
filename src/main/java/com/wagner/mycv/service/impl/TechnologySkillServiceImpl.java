package com.wagner.mycv.service.impl;

import com.wagner.mycv.model.entity.TechnologySkill;
import com.wagner.mycv.model.repository.TechnologySkillRepository;
import com.wagner.mycv.service.TechnologySkillService;
import com.wagner.mycv.web.dto.request.TechnologySkillRequestDto;
import com.wagner.mycv.web.dto.TechnologySkillDto;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TechnologySkillServiceImpl implements TechnologySkillService {

  private final TechnologySkillRepository technologySkillRepository;
  private final ModelMapper               modelMapper;

  @Autowired
  public TechnologySkillServiceImpl(TechnologySkillRepository technologySkillRepository) {
    this.technologySkillRepository = technologySkillRepository;
    this.modelMapper               = new ModelMapper();
  }

  @NotNull
  @Override
  public List<TechnologySkillDto> findAll() {
    return technologySkillRepository.findAll()
            .stream()
            .map(technologySkill -> modelMapper.map(technologySkill, TechnologySkillDto.class))
            .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Optional<TechnologySkillDto> find(long id) {
    Optional<TechnologySkill> technologySkill = technologySkillRepository.findById(id);

    return technologySkill.map(value -> modelMapper.map(value, TechnologySkillDto.class));
  }

  @NotNull
  @Override
  public TechnologySkillDto create(@NotNull TechnologySkillRequestDto request) {
    TechnologySkill technologySkill = modelMapper.map(request, TechnologySkill.class);
    technologySkillRepository.save(technologySkill);

    return modelMapper.map(technologySkill, TechnologySkillDto.class);
  }

  @NotNull
  @Override
  public List<TechnologySkillDto> createAll(@NotNull Iterable<TechnologySkillRequestDto> request) {
    List<TechnologySkill> technologySkills = new ArrayList<>();
    request.forEach(technologySkillRequestDto -> technologySkills.add(modelMapper.map(technologySkillRequestDto, TechnologySkill.class)));

    technologySkillRepository.saveAll(technologySkills);

    return technologySkills.stream()
            .map(technologySkill -> modelMapper.map(technologySkill, TechnologySkillDto.class))
            .collect(Collectors.toList());
  }

  @NotNull
  @Override
  public Optional<TechnologySkillDto> update(long id, @NotNull TechnologySkillRequestDto request) {
    Optional<TechnologySkill> technologySkillOptional = technologySkillRepository.findById(id);
    TechnologySkillDto technologySkillResponse        = null;

    if (technologySkillOptional.isPresent()) {
      TechnologySkill technologySkill = technologySkillOptional.get();
      modelMapper.map(request, technologySkill);

      technologySkillRepository.save(technologySkill);
      technologySkillResponse = modelMapper.map(technologySkill, TechnologySkillDto.class);
    }

    return Optional.ofNullable(technologySkillResponse);
  }

  @Override
  public boolean delete(long id) {
    if (technologySkillRepository.existsById(id)) {
      technologySkillRepository.deleteById(id);
      return true;
    }

    // entity doesn't exist
    return false;
  }

}
