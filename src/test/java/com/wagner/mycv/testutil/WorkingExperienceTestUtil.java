package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.WorkingExperience;
import com.wagner.mycv.web.dto.TechnologySkillDto;
import com.wagner.mycv.web.dto.WorkingExperienceDto;
import com.wagner.mycv.web.dto.request.WorkingExperienceRequestDto;
import org.assertj.core.api.LocalDateAssert;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

public class WorkingExperienceTestUtil {

  private WorkingExperienceTestUtil() {
    // has only static methods
  }

  /*--------------------WorkingExperience---------------------*/
  public static WorkingExperience createTestEntity() {
    return createTestEntity("Company AG", "Java Developer", LocalDate.of(2016,1,1), LocalDate.of(2019,1,1));
  }

  public static WorkingExperience createTestEntity(String company, String jobTitle) {
    return createTestEntity(company, jobTitle, LocalDate.of(2016,1,1), LocalDate.of(2019,1,1));
  }

  public static WorkingExperience createTestEntity(LocalDate begin, LocalDate end) {
    return createTestEntity("Company AG", "Java Developer", begin, end);
  }

  public static WorkingExperience createTestEntity(String company, String jobTitle, LocalDate begin, LocalDate end) {
    WorkingExperience workingExperience = new WorkingExperience();
    workingExperience.setBegin(begin);
    workingExperience.setEnd(end);
    workingExperience.setCompany(company);
    workingExperience.setFocalPoints(Arrays.asList("Working item description 1", "Working item description 2", "Working item description 3"));
    workingExperience.setJobTitle(jobTitle);
    workingExperience.setPlaceOfWork("Berlin");
    workingExperience.setUserId(UserTestUtil.USER_ID);

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
            .build();
  }

  public static WorkingExperienceRequestDto createDummyRequestDto() {
    return WorkingExperienceRequestDto.builder()
            .company("Dummy Company")
            .jobTitle("Dummy Developer")
            .begin(LocalDate.of(2010, 1, 1))
            .end(LocalDate.of(2019,1,1))
            .placeOfWork("Dummy town")
            .focalPoints(Arrays.asList(
                    "Point 1",
                    "Point 2",
                    "Point 3"))
            .build();
  }

  /*-------------------WorkingExperienceDto-------------------*/
  public static WorkingExperienceDto createFreelancerDto() {
    WorkingExperienceDto dto = new WorkingExperienceDto();
    dto.setId(1);
    dto.setCompany("Selbstständig");
    dto.setBegin(LocalDate.of(2011, 1, 1));
    dto.setEnd(LocalDate.of(2013, 9, 30));
    dto.setJobTitle("Webentwicklung, IT-Projekte, Beratung");
    dto.setPlaceOfWork("Berlin");
    dto.setFocalPoints(Arrays.asList("Realisierung von Internetauftritten und OnlineShops sowie Online-Marketing",
            "verschiedene Entwicklungsprojekte u. a. mit PHP, Zend Framework 2, HTML, CSS, JavaScript",
            "Dozententätigkeit (SQL Server 2010, Excel VBA)"));
    dto.setUserId(UserTestUtil.USER_ID);
    dto.setCreatedBy("Administrator");
    dto.setCreatedDate(LocalDate.now());
    dto.setLastModifiedBy("Administrator");
    dto.setLastModifiedDate(LocalDate.now());

    return dto;
  }

  public static WorkingExperienceDto createDummyDto(long id, LocalDate begin, LocalDate end) {
    WorkingExperienceDto dto = new WorkingExperienceDto();
    dto.setId(id);
    dto.setCompany("Company");
    dto.setBegin(begin);
    dto.setEnd(end);
    dto.setJobTitle("Job Title");
    dto.setPlaceOfWork("Berlin");
    dto.setFocalPoints(Collections.emptyList());
    dto.setUserId(UserTestUtil.USER_ID);
    dto.setCreatedBy("Administrator");
    dto.setCreatedDate(LocalDate.now());
    dto.setLastModifiedBy("Administrator");
    dto.setLastModifiedDate(LocalDate.now());

    return dto;
  }
}
