package com.wagner.mycv.model.entity;

import com.wagner.mycv.api.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "Language")
@Table(name = "language")
public class Language extends AbstractEntity {

  @Column(nullable = false)
  private String name;

  // ToDo DanielW: User A1, A2, B1 etc. for language level as alternative
  @Column(nullable = false)
  private byte level;

  @Column(nullable = false)
  private String userId;
}
