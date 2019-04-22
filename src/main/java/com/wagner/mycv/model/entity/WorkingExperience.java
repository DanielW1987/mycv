package com.wagner.mycv.model.entity;

import com.wagner.mycv.framework.jpa.entity.AbstractEntity;
import com.wagner.mycv.utils.CollectionUtil;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "WorkingExperience")
@Table(name = "working_experience")
public class WorkingExperience extends AbstractEntity {

  @Column(nullable = false)
  private String company;

  @Column(nullable = false)
  private LocalDate begin;

  private LocalDate end;

  @Column(nullable = false)
  private String jobTitle;

  @Column(nullable = false)
  private String placeOfWork;

  private String focalPoint1;
  private String focalPoint2;
  private String focalPoint3;
  private String focalPoint4;

  @Column(nullable = false)
  private String userId;

  public void setFocalPoints(List<String> focalPoints) {
    focalPoint1 = CollectionUtil.getOrDefault(focalPoints, 0);
    focalPoint2 = CollectionUtil.getOrDefault(focalPoints, 1);
    focalPoint3 = CollectionUtil.getOrDefault(focalPoints, 2);
    focalPoint4 = CollectionUtil.getOrDefault(focalPoints, 3);
  }

  public List<String> getFocalPoints() {
    return Stream.of(focalPoint1, focalPoint2, focalPoint3, focalPoint4)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
  }
}
