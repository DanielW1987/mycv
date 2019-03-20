package com.wagner.mycv.api.entity;

import java.time.LocalDate;

/**
 * Interface for entity objects.
 */
public interface Entity {

  long getId();

  LocalDate getCreatedDate();

  String getCreatedBy();

  LocalDate getLastModifiedDate();

  String getLastModifiedBy();

}
