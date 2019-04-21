package com.wagner.mycv.web.controller;

import com.wagner.mycv.api.controller.SimpleCrudRestController;
import com.wagner.mycv.model.exception.ErrorMessages;
import com.wagner.mycv.model.exception.RestRequestValidationException;
import com.wagner.mycv.service.WorkingExperienceService;
import com.wagner.mycv.web.dto.EducationDto;
import com.wagner.mycv.web.dto.WorkingExperienceDto;
import com.wagner.mycv.web.dto.request.EducationRequestDto;
import com.wagner.mycv.web.dto.request.TechnologySkillRequestDto;
import com.wagner.mycv.web.dto.request.WorkingExperienceRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/v1/working-experiences")
public class WorkingExperienceRestController implements SimpleCrudRestController<WorkingExperienceRequestDto, WorkingExperienceDto> {

  private final WorkingExperienceService workingExperienceService;

  @Autowired
  public WorkingExperienceRestController(WorkingExperienceService workingExperienceService) {
    this.workingExperienceService = workingExperienceService;
  }

  @Override
  @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<WorkingExperienceDto> get(@PathVariable long id) {
    Optional<WorkingExperienceDto> workingExperienceDto = workingExperienceService.find(id);

    return ResponseEntity.of(workingExperienceDto);
  }

  @Override
  @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<List<WorkingExperienceDto>> getAll() {
    List<WorkingExperienceDto> workingExperienceDtos = workingExperienceService.findAll();

    return ResponseEntity.ok(workingExperienceDtos);
  }

  @Override
  @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
               consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<WorkingExperienceDto> create(@Valid @RequestBody WorkingExperienceRequestDto request, BindingResult bindingResult) {
    validateRequest(bindingResult);

    WorkingExperienceDto workingExperienceDto = workingExperienceService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(workingExperienceDto);
  }

  @Override
  @PutMapping(path = "/{id}",
              produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
              consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<WorkingExperienceDto> update(@PathVariable long id,
                                                     @Valid @RequestBody WorkingExperienceRequestDto request,
                                                     BindingResult bindingResult) {
    validateRequest(bindingResult);

    Optional<WorkingExperienceDto> workingExperienceDto = workingExperienceService.update(id, request);
    return ResponseEntity.of(workingExperienceDto);
  }

  @Override
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    boolean success = workingExperienceService.delete(id);

    return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
  }
}