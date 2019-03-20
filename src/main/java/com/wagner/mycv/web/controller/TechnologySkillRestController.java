package com.wagner.mycv.web.controller;

import com.wagner.mycv.api.controller.SimpleCrudRestController;
import com.wagner.mycv.service.TechnologySkillService;
import com.wagner.mycv.web.dto.TechnologySkillDto;
import com.wagner.mycv.web.dto.request.TechnologySkillRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest/v1/technology-skills")
public class TechnologySkillRestController implements SimpleCrudRestController<TechnologySkillRequestDto, TechnologySkillDto> {

  private final TechnologySkillService technologySkillService;

  @Autowired
  public TechnologySkillRestController(TechnologySkillService technologySkillService) {
    this.technologySkillService = technologySkillService;
  }

  @Override
  public ResponseEntity<TechnologySkillDto> get(long id) {
    return null;
  }

  @Override
  public ResponseEntity<List<TechnologySkillDto>> getAll() {
    return null;
  }

  @Override
  public ResponseEntity<TechnologySkillDto> create(@Valid TechnologySkillRequestDto request, BindingResult bindingResult) {
    return null;
  }

  @Override
  public ResponseEntity<TechnologySkillDto> update(long id, @Valid TechnologySkillRequestDto request, BindingResult bindingResult) {
    return null;
  }

  @Override
  public ResponseEntity<Void> delete(long id) {
    return null;
  }
}
