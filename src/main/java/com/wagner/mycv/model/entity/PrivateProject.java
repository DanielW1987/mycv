package com.wagner.mycv.model.entity;

import com.wagner.mycv.api.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "PrivateProject")
@Table(name = "private_project")
public class PrivateProject extends AbstractEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String technologiesUsed;

  private String vcsUrl;
  private String description;

  @Column(nullable = false)
  private String userId;

  public void setTechnologiesUsed(List<String> technologiesUsed) {
    this.technologiesUsed = String.join(", ", technologiesUsed);
  }

}
