package com.wagner.mycv.model.entity;

import com.wagner.mycv.framework.jpa.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "Certification")
@Table(name = "certification")
public class Certification extends AbstractEntity {

  public static final long serialVersionUID = 1L;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private LocalDate dateOfAchievement;

  private String certificate; // ToDo: should be a file

  @Column(nullable = false)
  private String userId;

}
