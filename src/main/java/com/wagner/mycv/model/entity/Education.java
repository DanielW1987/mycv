package com.wagner.mycv.model.entity;

import com.wagner.mycv.framework.jpa.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity(name = "Education")
@Table(name = "education")
public class Education extends AbstractEntity {

  public static final long serialVersionUID = 1L;

  @Column(nullable = false)
  private String facility;

  @Column(nullable = false)
  private LocalDate begin;

  @Column(nullable = false)
  private LocalDate end;

  @Column(nullable = false)
  private String graduation;

  @Column(nullable = false)
  private String userId;
}
