package com.wagner.mycv.web.controller;

import com.wagner.mycv.api.controller.SimpleCrudRestController;
import com.wagner.mycv.service.PrivateProjectService;
import com.wagner.mycv.web.dto.PrivateProjectDto;
import com.wagner.mycv.web.dto.request.PrivateProjectRequestDto;
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
@RequestMapping("/rest/v1/private-projects")
public class PrivateProjectRestController implements SimpleCrudRestController<PrivateProjectRequestDto, PrivateProjectDto> {

  private final PrivateProjectService privateProjectService;

  @Autowired
  public PrivateProjectRestController(PrivateProjectService privateProjectService) {
    this.privateProjectService = privateProjectService;
  }

  @Override
  @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<PrivateProjectDto> get(@PathVariable long id) {
    Optional<PrivateProjectDto> privateProjectDto = privateProjectService.find(id);

    return ResponseEntity.of(privateProjectDto);
  }

  @Override
  @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<List<PrivateProjectDto>> getAll() {
    List<PrivateProjectDto> privateProjectDtos = privateProjectService.findAll();

    return ResponseEntity.ok(privateProjectDtos);
  }

  @Override
  @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
               consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<PrivateProjectDto> create(@Valid @RequestBody PrivateProjectRequestDto request, BindingResult bindingResult) {
    validateRequest(bindingResult);

    PrivateProjectDto privateProjectDto = privateProjectService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(privateProjectDto);
  }

  @Override
  @PutMapping(path = "/{id}",
              produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
              consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<PrivateProjectDto> update(@PathVariable long id,
                                                  @Valid @RequestBody PrivateProjectRequestDto request,
                                                  BindingResult bindingResult) {
    validateRequest(bindingResult);

    Optional<PrivateProjectDto> privateProjectDto = privateProjectService.update(id, request);
    return ResponseEntity.of(privateProjectDto);
  }

  @Override
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    boolean success = privateProjectService.delete(id);

    return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
  }
}
