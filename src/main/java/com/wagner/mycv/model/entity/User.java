package com.wagner.mycv.model.entity;

import com.wagner.mycv.framework.jpa.entity.AbstractEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "User")
@Table(name = "user")
@Getter
@Setter
public class User extends AbstractEntity {

  @Column(unique = true, nullable = false)
  private String userId;

}
