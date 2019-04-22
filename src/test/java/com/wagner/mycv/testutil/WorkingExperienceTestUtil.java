package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.WorkingExperience;
import com.wagner.mycv.web.dto.request.WorkingExperienceRequestDto;

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
  public static WorkingExperienceRequestDto createJavaConsultantRequestDto() {
    return WorkingExperienceRequestDto.builder()
            .company("John Doe Company")
            .jobTitle("Java Technical Consultant")
            .begin(LocalDate.of(2016, 1, 1))
            .end(LocalDate.of(2017, 2, 28))
            .placeOfWork("Berlin")
            .focalPoints(Arrays.asList(
                    "Aufbau eines agilen Entwicklungsteams am Standort Mönchengladbach",
                    "Einführung qualitätssichernder Standards (Coding Conventions, Code Reviews, Regressionstests)",
                    "Kundenindividuelle Java-Projekte"))
            .userId(UserTestUtil.USER_ID.toString())
            .build();
  }

  public static WorkingExperienceRequestDto createJavaDeveloperRequestDto() {
    return WorkingExperienceRequestDto.builder()
            .company("John Doe Company")
            .jobTitle("Java Developer")
            .begin(LocalDate.of(2017, 3, 1))
            .placeOfWork("Berlin")
            .focalPoints(Arrays.asList(
                    "davon 1,5 Jahre Teamleiter eines 5-köpfigen Teams",
                    "Entwicklung von Anwendungen zur automatisierten Erstellung von Konzernabschlüssen (REST-Backend, Java Swing)",
                    "Software-Security (Authentifizierung, Autorisierung, Verschlüsselung, Transparenz)",
                    "Leitung von Kooperationsprojekten mit dem Masterstudiengang Wirtschaftsinformatik der HTW Berlin"))
            .userId(UserTestUtil.USER_ID.toString())
            .build();
  }

  /*-------------------WorkingExperienceDto-------------------*/
}
