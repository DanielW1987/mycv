package com.wagner.mycv.web.controller;

import com.wagner.mycv.framework.controller.SimpleCrudRestController;
import com.wagner.mycv.service.ProgrammingProjectService;
import com.wagner.mycv.web.dto.ProgrammingProjectDto;
import com.wagner.mycv.web.dto.request.ProgrammingProjectRequestDto;
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
@RequestMapping("/rest/v1/programming-projects")
public class ProgrammingProjectsRestController implements SimpleCrudRestController<ProgrammingProjectRequestDto, ProgrammingProjectDto> {

  private final ProgrammingProjectService programmingProjectService;

  @Autowired
  public ProgrammingProjectsRestController(ProgrammingProjectService programmingProjectService) {
    this.programmingProjectService = programmingProjectService;
  }

  @Override
  @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<ProgrammingProjectDto> get(@PathVariable long id) {
    Optional<ProgrammingProjectDto> privateProjectDto = programmingProjectService.find(id);

    return ResponseEntity.of(privateProjectDto);
  }

  @Override
  @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<List<ProgrammingProjectDto>> getAll() {
    List<ProgrammingProjectDto> programmingProjectDtos = programmingProjectService.findAll();

    return ResponseEntity.ok(programmingProjectDtos);
  }

  @Override
  @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
               consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<ProgrammingProjectDto> create(@Valid @RequestBody ProgrammingProjectRequestDto request,
                                                      BindingResult bindingResult) {
    validateRequest(bindingResult);

    ProgrammingProjectDto programmingProjectDto = programmingProjectService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(programmingProjectDto);
  }

  @Override
  @PutMapping(path = "/{id}",
              produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
              consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<ProgrammingProjectDto> update(@PathVariable long id,
                                                      @Valid @RequestBody ProgrammingProjectRequestDto request,
                                                      BindingResult bindingResult) {
    validateRequest(bindingResult);

    Optional<ProgrammingProjectDto> privateProjectDto = programmingProjectService.update(id, request);
    return ResponseEntity.of(privateProjectDto);
  }

  @Override
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    boolean success = programmingProjectService.delete(id);

    return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
  }
}
