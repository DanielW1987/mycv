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

  @Column(nullable = false)
  private byte level;

  @Column(nullable = false)
  private String userId;
}
