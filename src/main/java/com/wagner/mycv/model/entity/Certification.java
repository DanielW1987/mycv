package com.wagner.mycv.model.entity;

import com.wagner.mycv.api.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "Certification")
@Table(name = "certification")
public class Certification extends AbstractEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private LocalDate dateOfAchievement;

  private String certificate; // ToDo: should be a file

  @Column(nullable = false)
  private String userId;

}
