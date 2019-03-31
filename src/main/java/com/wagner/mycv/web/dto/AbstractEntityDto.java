package com.wagner.mycv.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class AbstractEntityDto {

  protected String createdDate;
  protected String createdBy;
  protected String lastModifiedDate;
  protected String lastModifiedBy;
  protected String userId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AbstractEntityDto that = (AbstractEntityDto) o;
    return userId.equals(that.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId);
  }
}
