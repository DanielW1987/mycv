package com.wagner.mycv.web.controller;

import com.wagner.mycv.framework.controller.SimpleCrudRestController;
import com.wagner.mycv.service.EducationService;
import com.wagner.mycv.web.dto.EducationDto;
import com.wagner.mycv.web.dto.request.EducationRequestDto;
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
@RequestMapping("/rest/v1/educations")
public class EducationsRestController implements SimpleCrudRestController<EducationRequestDto, EducationDto> {

  private final EducationService educationService;

  @Autowired
  public EducationsRestController(EducationService educationService) {
    this.educationService = educationService;
  }

  @Override
  @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<EducationDto> get(@PathVariable long id) {
    Optional<EducationDto> educationDto = educationService.find(id);

    return ResponseEntity.of(educationDto);
  }

  @Override
  @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<List<EducationDto>> getAll() {
    List<EducationDto> educations = educationService.findAll();

    return ResponseEntity.ok(educations);
  }

  @Override
  @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
               consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<EducationDto> create(@Valid @RequestBody EducationRequestDto request, BindingResult bindingResult) {
    validateRequest(bindingResult);

    EducationDto educationDto = educationService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(educationDto);
  }

  @Override
  @PutMapping(path = "/{id}",
              produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
              consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<EducationDto> update(@PathVariable long id,
                                             @Valid @RequestBody EducationRequestDto request,
                                             BindingResult bindingResult) {
    validateRequest(bindingResult);

    Optional<EducationDto> educationDto = educationService.update(id, request);
    return ResponseEntity.of(educationDto);
  }

  @Override
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    boolean success = educationService.delete(id);

    return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
  }
}
