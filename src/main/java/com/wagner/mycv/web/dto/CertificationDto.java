package com.wagner.mycv.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class CertificationDto extends AbstractEntityDto {

  private long      id;
  private String    name;
  private String    dateOfAchievement;
  private String    certificate; // ToDo: should be a file

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }

    CertificationDto that = (CertificationDto) o;
    return     id == that.id
            && name.equals(that.name)
            && Objects.equals(dateOfAchievement, that.dateOfAchievement)
            && Objects.equals(certificate, that.certificate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id, name, dateOfAchievement, certificate);
  }
}
