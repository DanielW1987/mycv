package com.wagner.mycv.api.service;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for CRUD-Operations.
 * @param <T> DTO for Request
 * @param <U> DTO for Response
 */
public interface SimpleCrudService<T, U> {

  List<U> findAll();

  Optional<U> find(long id);

  U create(T request);

  List<U> createAll(Iterable<T> request);

  Optional<U> update(long id, T request);

  boolean delete(long id);

}
