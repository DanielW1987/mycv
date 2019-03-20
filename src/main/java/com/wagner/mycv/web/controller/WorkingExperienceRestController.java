package com.wagner.mycv.web.controller;

import com.wagner.mycv.api.controller.SimpleCrudRestController;
import com.wagner.mycv.service.WorkingExperienceService;
import com.wagner.mycv.web.dto.WorkingExperienceDto;
import com.wagner.mycv.web.dto.request.WorkingExperienceRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest/v1/working-experiences")
public class WorkingExperienceRestController implements SimpleCrudRestController<WorkingExperienceRequestDto, WorkingExperienceDto> {

  private final WorkingExperienceService workingExperienceService;

  @Autowired
  public WorkingExperienceRestController(WorkingExperienceService workingExperienceService) {
    this.workingExperienceService = workingExperienceService;
  }

  @Override
  public ResponseEntity<WorkingExperienceDto> get(long id) {
    return null;
  }

  @Override
  public ResponseEntity<List<WorkingExperienceDto>> getAll() {
    return null;
  }

  @Override
  public ResponseEntity<WorkingExperienceDto> create(@Valid WorkingExperienceRequestDto request, BindingResult bindingResult) {
    return null;
  }

  @Override
  public ResponseEntity<WorkingExperienceDto> update(long id, @Valid WorkingExperienceRequestDto request, BindingResult bindingResult) {
    return null;
  }

  @Override
  public ResponseEntity<Void> delete(long id) {
    return null;
  }
}