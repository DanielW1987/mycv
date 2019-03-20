package com.wagner.mycv.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
public class AbstractDto {

  protected String createdDate;
  protected String createdBy;
  protected String lastModifiedDate;
  protected String lastModifiedBy;
  protected String userId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AbstractDto that = (AbstractDto) o;
    return createdDate.equals(that.createdDate) &&
            createdBy.equals(that.createdBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            userId.equals(that.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(createdDate, createdBy, lastModifiedDate, lastModifiedBy, userId);
  }
}
