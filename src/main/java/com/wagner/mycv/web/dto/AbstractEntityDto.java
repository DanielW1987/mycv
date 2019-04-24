package com.wagner.mycv.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
public class AbstractEntityDto {

  protected long      id;
  protected LocalDate createdDate;
  protected String    createdBy;
  protected LocalDate lastModifiedDate;
  protected String    lastModifiedBy;
  protected String    userId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AbstractEntityDto that = (AbstractEntityDto) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
