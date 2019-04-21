package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.WorkingExperience;

import java.time.LocalDate;
import java.util.Arrays;

public class WorkingExperienceTestUtil {

  private WorkingExperienceTestUtil() {
    // has only static methods
  }

  /*--------------------WorkingExperience---------------------*/
  public static WorkingExperience createTestEntity() {
    return createTestEntity("Company AG", "Java Developer");
  }

  public static WorkingExperience createTestEntity(String company, String jobTitle) {
    WorkingExperience workingExperience = new WorkingExperience();
    workingExperience.setBegin(LocalDate.of(2016,1,1));
    workingExperience.setEnd(LocalDate.of(2019,1,1));
    workingExperience.setCompany(company);
    workingExperience.setFocalPoints(Arrays.asList("Working item description 1", "Working item description 2", "Working item description 3"));
    workingExperience.setJobTitle(jobTitle);
    workingExperience.setPlaceOfWork("Berlin");
    workingExperience.setUserId(UserTestUtil.USER_ID.toString());

    return workingExperience;
  }

  /*---------------WorkingExperienceRequestDto----------------*/


  /*-------------------WorkingExperienceDto-------------------*/
}
