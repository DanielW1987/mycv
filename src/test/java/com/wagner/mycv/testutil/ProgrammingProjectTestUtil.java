package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.ProgrammingProject;
import com.wagner.mycv.web.dto.ProgrammingProjectDto;
import com.wagner.mycv.web.dto.request.ProgrammingProjectRequestDto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


public class ProgrammingProjectTestUtil {

  private ProgrammingProjectTestUtil() {
    // has only static methods
  }

  /*--------------------ProgrammingProject---------------------*/
  public static ProgrammingProject createTestEntity() {
    return createTestEntity("My private project", Arrays.asList("Java", "Spring", "Jenkins", "Angular"));
  }

  public static ProgrammingProject createTestEntity(String name, List<String> technologiesUsed) {
    ProgrammingProject programmingProject = new ProgrammingProject();
    programmingProject.setName(name);
    programmingProject.setDescription("A long and good description");
    programmingProject.setTechnologiesUsed(technologiesUsed);
    programmingProject.setVcsUrl("https://www.githib.com/my/project");
    programmingProject.setUserId(UserTestUtil.USER_ID);

    return programmingProject;
  }

  /*---------------ProgrammingProjectRequestDto----------------*/
  public static ProgrammingProjectRequestDto createMyCvProjectRequestDto() {
    return ProgrammingProjectRequestDto.builder()
            .name("My Beautiful CV")
            .technologiesUsed(Arrays.asList("Spring Boot", "Angular"))
            .description("Lorem ipsum...")
            .vcsUrl("https://www.bitbucket.com/foobar")
            .build();
  }

  public static ProgrammingProjectRequestDto createExamedyProjectRequestDto() {
    return ProgrammingProjectRequestDto.builder()
            .name("Examedy")
            .technologiesUsed(Arrays.asList("Spring Boot", "Angular"))
            .description("Lorem ipsum...")
            .vcsUrl("https://www.github.com/foobar")
            .build();
  }

  /*-------------------ProgrammingProjectDto-------------------*/
  public static ProgrammingProjectDto creteMyBeautifulCvProjectDto() {
    ProgrammingProjectDto dto = new ProgrammingProjectDto();
    dto.setId(1);
    dto.setName("My Beautiful CV");
    dto.setDescription("Lorem ipsum...");
    dto.setTechnologiesUsed(Arrays.asList("Spring Boot", "Angular"));
    dto.setVcsUrl("https://www.bitbucket.com/foobar");
    dto.setUserId(UserTestUtil.USER_ID);
    dto.setCreatedBy("Administrator");
    dto.setCreatedDate(LocalDate.now());
    dto.setLastModifiedBy("Administrator");
    dto.setLastModifiedDate(LocalDate.now());

    return dto;
  }
}
