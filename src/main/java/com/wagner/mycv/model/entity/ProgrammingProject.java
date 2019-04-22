package com.wagner.mycv.model.entity;

import com.google.common.collect.Lists;
import com.wagner.mycv.framework.jpa.entity.AbstractEntity;
import com.wagner.mycv.utils.CollectionUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity(name = "PrivateProject")
@Table(name = "private_project")
public class ProgrammingProject extends AbstractEntity {

  public static final long serialVersionUID = 1L;

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

  public List<String> getTechnologiesUsed() {
    return Lists.newArrayList(CollectionUtil.characterSeparatedStringToList(technologiesUsed));
  }

}
