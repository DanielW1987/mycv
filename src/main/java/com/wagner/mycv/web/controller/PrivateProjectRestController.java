package com.wagner.mycv.web.controller;

import com.wagner.mycv.api.controller.SimpleCrudRestController;
import com.wagner.mycv.model.entity.PrivateProject;
import com.wagner.mycv.service.PrivateProjectService;
import com.wagner.mycv.web.dto.request.PrivateProjectRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest/v1/private-projects")
public class PrivateProjectRestController implements SimpleCrudRestController<PrivateProjectRequestDto, PrivateProject> {

  private final PrivateProjectService privateProjectService;

  @Autowired
  public PrivateProjectRestController(PrivateProjectService privateProjectService) {
    this.privateProjectService = privateProjectService;
  }

  @Override
  public ResponseEntity<PrivateProject> get(long id) {
    return null;
  }

  @Override
  public ResponseEntity<List<PrivateProject>> getAll() {
    return null;
  }

  @Override
  public ResponseEntity<PrivateProject> create(@Valid PrivateProjectRequestDto request, BindingResult bindingResult) {
    return null;
  }

  @Override
  public ResponseEntity<PrivateProject> update(long id, @Valid PrivateProjectRequestDto request, BindingResult bindingResult) {
    return null;
  }

  @Override
  public ResponseEntity<Void> delete(long id) {
    return null;
  }
}
