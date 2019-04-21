package com.wagner.mycv.web.controller;

import com.wagner.mycv.api.controller.SimpleCrudRestController;
import com.wagner.mycv.service.LanguageService;
import com.wagner.mycv.web.dto.LanguageDto;
import com.wagner.mycv.web.dto.request.LanguageRequestDto;
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
@RequestMapping("/rest/v1/languages")
public class LanguageRestController implements SimpleCrudRestController<LanguageRequestDto, LanguageDto> {

  private final LanguageService languageService;

  @Autowired
  public LanguageRestController(LanguageService languageService) {
    this.languageService = languageService;
  }

  @Override
  @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<LanguageDto> get(@PathVariable long id) {
    Optional<LanguageDto> languageDto = languageService.find(id);

    return ResponseEntity.of(languageDto);
  }

  @Override
  @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<List<LanguageDto>> getAll() {
    List<LanguageDto> languages = languageService.findAll();

    return ResponseEntity.ok(languages);
  }

  @Override
  @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
               consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<LanguageDto> create(@Valid @RequestBody LanguageRequestDto request, BindingResult bindingResult) {
    validateRequest(bindingResult);

    LanguageDto languageDto = languageService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(languageDto);
  }

  @Override
  @PutMapping(path = "/{id}",
              produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
              consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<LanguageDto> update(@PathVariable long id,
                                            @Valid @RequestBody LanguageRequestDto request,
                                            BindingResult bindingResult) {
    validateRequest(bindingResult);

    Optional<LanguageDto> languageDto = languageService.update(id, request);
    return ResponseEntity.of(languageDto);
  }

  @Override
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    boolean success = languageService.delete(id);

    return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
  }

}
