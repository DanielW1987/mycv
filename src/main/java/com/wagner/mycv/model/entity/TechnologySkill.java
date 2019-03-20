package com.wagner.mycv.model.entity;

import com.wagner.mycv.api.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "TechnologySkill")
@Table(name = "technology_skill")
public class TechnologySkill extends AbstractEntity {

  @Column(nullable = false)
  private String category;

  @Column(nullable = false)
  private String skillNames;

  @Column(nullable = false)
  private String userId;

  public void setSkillNames(List<String> skillNames) {
    this.skillNames = String.join(", ", skillNames);
  }
}
