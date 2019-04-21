package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.Education;

import java.time.LocalDate;
import java.util.UUID;

public class EducationTestUtil {

  private EducationTestUtil() {
    // has only static methods
  }

  /*--------------------Education---------------------*/
  public static Education createTestEntity() {
    return createTestEntity("HTW Berlin", "Master of Science");
  }

  public static Education createTestEntity(String facility, String graduation) {
    Education education = new Education();
    education.setBegin(LocalDate.of(2017, 1, 1));
    education.setEnd(LocalDate.of(2019, 1, 1));
    education.setFacility(facility);
    education.setGraduation(graduation);
    education.setUserId(UserTestUtil.USER_ID.toString());

    return education;
  }

  /*---------------EducationRequestDto----------------*/


  /*-------------------EducationDto-------------------*/

}
