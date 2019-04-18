package com.wagner.mycv.model.entity;

import com.wagner.mycv.api.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "UserProfile")
@Table(name = "user_profile")
public class UserProfile extends AbstractEntity {

  @Column(nullable = false)
  private String firstName;

  private String lastName;
  private String currentJob;
  private String placeOfResidence;

  @Column(unique = true)
  private String email;

  private String mobilePhone;
  private String profileImage;

  @Column(nullable = false)
  private String userId;

}
