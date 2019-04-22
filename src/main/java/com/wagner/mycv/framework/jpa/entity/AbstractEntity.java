package com.wagner.mycv.framework.jpa.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

/**
 * Abstract super class for entity.
 *
 * author: Daniel Wagner
 * date: 2019-03-20
 */
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED) // for hibernate
@EntityListeners({AuditingEntityListener.class})
public abstract class AbstractEntity implements Entity, Serializable {

  public static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdDate;

  @CreatedBy
  private String createdBy;

  @LastModifiedDate
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastModifiedDate;

  @LastModifiedBy
  private String lastModifiedBy;

  @Override
  public long getId() {
    return id;
  }

  @Override
  public LocalDate getCreatedDate() {
    return createdDate != null ? createdDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
  }

  @Override
  public String getCreatedBy() {
    return createdBy;
  }

  @Override
  public LocalDate getLastModifiedDate() {
    return lastModifiedDate != null ? lastModifiedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
  }

  @Override
  public String getLastModifiedBy() {
    return lastModifiedBy;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AbstractEntity)) {
      return false;
    }
    AbstractEntity that = (AbstractEntity) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

}