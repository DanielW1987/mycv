package com.wagner.mycv.model.entity;

import com.wagner.mycv.api.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "User")
@Table(name = "user")
@Getter
@Setter
public class User extends AbstractEntity {

  @Column(unique = true, nullable = false)
  private String userId;

}
