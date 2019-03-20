package com.wagner.mycv.model.entity;

import com.wagner.mycv.api.entity.AbstractEntity;
import com.wagner.mycv.utils.CollectionUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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
    focalPoint1 = CollectionUtil.getFromListOrDefault(focalPoints, 0);
    focalPoint2 = CollectionUtil.getFromListOrDefault(focalPoints, 1);
    focalPoint3 = CollectionUtil.getFromListOrDefault(focalPoints, 2);
    focalPoint4 = CollectionUtil.getFromListOrDefault(focalPoints, 3);
  }
}
