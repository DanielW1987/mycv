package com.wagner.mycv.web.controller;

import com.wagner.mycv.framework.controller.SimpleCrudRestController;
import com.wagner.mycv.service.TechnologySkillService;
import com.wagner.mycv.web.dto.TechnologySkillDto;
import com.wagner.mycv.web.dto.request.TechnologySkillRequestDto;
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
@RequestMapping("/rest/v1/technology-skills")
public class TechnologySkillsRestController implements SimpleCrudRestController<TechnologySkillRequestDto, TechnologySkillDto> {

  private final TechnologySkillService technologySkillService;

  @Autowired
  public TechnologySkillsRestController(TechnologySkillService technologySkillService) {
    this.technologySkillService = technologySkillService;
  }

  @Override
  @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<TechnologySkillDto> get(@PathVariable long id) {
    Optional<TechnologySkillDto> technologySkillDto = technologySkillService.find(id);

    return ResponseEntity.of(technologySkillDto);
  }

  @Override
  @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<List<TechnologySkillDto>> getAll() {
    List<TechnologySkillDto> technologySkillDtos = technologySkillService.findAll();

    return ResponseEntity.ok(technologySkillDtos);
  }

  @Override
  @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
               consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<TechnologySkillDto> create(@Valid @RequestBody TechnologySkillRequestDto request, BindingResult bindingResult) {
    validateRequest(bindingResult);

    TechnologySkillDto technologySkillDto = technologySkillService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(technologySkillDto);
  }

  @Override
  @PutMapping(path = "/{id}",
              produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
              consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<TechnologySkillDto> update(@PathVariable long id,
                                                   @Valid @RequestBody TechnologySkillRequestDto request,
                                                   BindingResult bindingResult) {
    validateRequest(bindingResult);

    Optional<TechnologySkillDto> technologySkillDto = technologySkillService.update(id, request);
    return ResponseEntity.of(technologySkillDto);
  }

  @Override
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    boolean success = technologySkillService.delete(id);

    return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
  }
}
