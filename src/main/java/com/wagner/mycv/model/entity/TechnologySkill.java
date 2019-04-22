package com.wagner.mycv.model.entity;

import com.google.common.collect.Lists;
import com.wagner.mycv.framework.jpa.entity.AbstractEntity;
import com.wagner.mycv.utils.CollectionUtil;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

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

  public List<String> getSkillNames() {
    return Lists.newArrayList(CollectionUtil.characterSeparatedStringToList(skillNames));
  }
}
