package com.wagner.mycv.service.impl;

import com.wagner.mycv.model.entity.TechnologySkill;
import com.wagner.mycv.model.repository.TechnologySkillRepository;
import com.wagner.mycv.service.TechnologySkillService;
import com.wagner.mycv.web.dto.request.TechnologySkillRequestDto;
import com.wagner.mycv.web.dto.TechnologySkillDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

  @Override
  public List<TechnologySkillDto> findAll() {
    Sort sort = new Sort(Sort.Direction.DESC, "end");

    return technologySkillRepository.findAll(sort)
            .stream()
            .map(technologySkill -> modelMapper.map(technologySkill, TechnologySkillDto.class))
            .collect(Collectors.toList());
  }

  @Override
  public Optional<TechnologySkillDto> find(long id) {
    Optional<TechnologySkill> technologySkill = technologySkillRepository.findById(id);

    return technologySkill.map(value -> modelMapper.map(value, TechnologySkillDto.class));
  }

  @Override
  public TechnologySkillDto create(TechnologySkillRequestDto request) {
    TechnologySkill technologySkill = modelMapper.map(request, TechnologySkill.class);
    technologySkillRepository.save(technologySkill);

    return modelMapper.map(technologySkill, TechnologySkillDto.class);
  }

  @Override
  public List<TechnologySkillDto> createAll(Iterable<TechnologySkillRequestDto> request) {
    List<TechnologySkill> technologySkills = new ArrayList<>();
    request.forEach(technologySkillRequestDto -> technologySkills.add(modelMapper.map(technologySkillRequestDto, TechnologySkill.class)));

    technologySkillRepository.saveAll(technologySkills);

    return technologySkills.stream()
            .map(technologySkill -> modelMapper.map(technologySkill, TechnologySkillDto.class))
            .collect(Collectors.toList());
  }

  @Override
  public Optional<TechnologySkillDto> update(long id, TechnologySkillRequestDto request) {
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

//  private TechnologySkill mapRequestDto(TechnologySkillRequestDto request) {
//    TechnologySkill technologySkill = modelMapper.map(request, TechnologySkill.class);
//    technologySkill.setSkillNames(String.join(", ", request.getSkillNames()));
//
//    return technologySkill;
//  }
}
