package com.wagner.mycv.framework.controller;

import com.wagner.mycv.model.exception.ErrorMessages;
import com.wagner.mycv.model.exception.RestRequestValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;

/**
 * Interface for basic CRUD-Operations in REST controller.
 * @param <T> DTO for Request
 * @param <U> DTO for Response
 */
public interface SimpleCrudRestController<T, U> {

  ResponseEntity<U> get(long id);

  ResponseEntity<List<U>> getAll();

  // ResponseEntity<List<U>> getAll(int page, int limit);

  ResponseEntity<U> create(@Valid T request, BindingResult bindingResult);

  ResponseEntity<U> update(long id, @Valid T request, BindingResult bindingResult);

  ResponseEntity<Void> delete(long id);

  default void validateRequest(BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new RestRequestValidationException(ErrorMessages.VALIDATION_ERROR.toDisplayString(), bindingResult.getFieldErrors());
    }
  }

}
