package com.wagner.mycv.web.controller;

import com.wagner.mycv.api.controller.SimpleCrudRestController;
import com.wagner.mycv.service.LanguageService;
import com.wagner.mycv.web.dto.LanguageDto;
import com.wagner.mycv.web.dto.request.LanguageRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest/v1/languages")
public class LanguageRestController implements SimpleCrudRestController<LanguageRequestDto, LanguageDto> {

  private final LanguageService languageService;

  @Autowired
  public LanguageRestController(LanguageService languageService) {
    this.languageService = languageService;
  }

  @Override
  public ResponseEntity<LanguageDto> get(long id) {
    return null;
  }

  @Override
  public ResponseEntity<List<LanguageDto>> getAll() {
    return null;
  }

  @Override
  public ResponseEntity<LanguageDto> create(@Valid LanguageRequestDto request, BindingResult bindingResult) {
    return null;
  }

  @Override
  public ResponseEntity<LanguageDto> update(long id, @Valid LanguageRequestDto request, BindingResult bindingResult) {
    return null;
  }

  @Override
  public ResponseEntity<Void> delete(long id) {
    return null;
  }
}
