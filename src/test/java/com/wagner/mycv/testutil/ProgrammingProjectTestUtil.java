package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.ProgrammingProject;
import com.wagner.mycv.web.dto.request.ProgrammingProjectRequestDto;

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
    programmingProject.setUserId(UserTestUtil.USER_ID.toString());

    return programmingProject;
  }

  /*---------------ProgrammingProjectRequestDto----------------*/
  public static ProgrammingProjectRequestDto createMyCvProjectRequestDto() {
    return ProgrammingProjectRequestDto.builder()
            .name("My Beautiful CurriculumVitae")
            .technologiesUsed(Arrays.asList("Spring Boot", "Angular"))
            .description("Lorem ipsum...")
            .vcsUrl("https://www.bitbucket.com/foobar")
            .userId(UserTestUtil.USER_ID.toString())
            .build();
  }

  public static ProgrammingProjectRequestDto createExamedyProjectRequestDto() {
    return ProgrammingProjectRequestDto.builder()
            .name("Examedy")
            .technologiesUsed(Arrays.asList("Spring Boot", "Angular"))
            .description("Lorem ipsum...")
            .vcsUrl("https://www.github.com/foobar")
            .userId(UserTestUtil.USER_ID.toString())
            .build();
  }

  /*-------------------ProgrammingProjectDto-------------------*/
}
