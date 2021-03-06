package com.wagner.mycv.web.controller;

import com.wagner.mycv.framework.controller.SimpleCrudRestController;
import com.wagner.mycv.service.CertificationService;
import com.wagner.mycv.web.dto.request.CertificationRequestDto;
import com.wagner.mycv.web.dto.CertificationDto;
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
@RequestMapping("/rest/v1/certifications")
public class CertificationsRestController implements SimpleCrudRestController<CertificationRequestDto, CertificationDto> {

  private final CertificationService certificationService;

  @Autowired
  public CertificationsRestController(CertificationService certificationService) {
    this.certificationService = certificationService;
  }

  @Override
  @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<CertificationDto> get(@PathVariable long id) {
    Optional<CertificationDto> certificationDto = certificationService.find(id);

    return ResponseEntity.of(certificationDto);
  }

  @Override
  @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<List<CertificationDto>> getAll() {
    List<CertificationDto> certificationDtos = certificationService.findAll();

    return ResponseEntity.ok(certificationDtos);
  }

  @Override
  @PostMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
               consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<CertificationDto> create(@Valid @RequestBody CertificationRequestDto request, BindingResult bindingResult) {
    validateRequest(bindingResult);

    CertificationDto certification = certificationService.create(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(certification);
  }

  @Override
  @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
                            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<CertificationDto> update(@PathVariable long id,
                                                 @Valid @RequestBody CertificationRequestDto request,
                                                 BindingResult bindingResult) {
    validateRequest(bindingResult);

    Optional<CertificationDto> certification = certificationService.update(id, request);
    return ResponseEntity.of(certification);
  }

  @Override
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable long id) {
    boolean success = certificationService.delete(id);

    return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
  }
}
