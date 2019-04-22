package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.TechnologySkill;
import com.wagner.mycv.web.dto.request.TechnologySkillRequestDto;

import java.util.Arrays;
import java.util.List;

public class TechnologySkillTestUtil {

  private TechnologySkillTestUtil() {
    // has only static methods
  }

  /*--------------------TechnologySkill---------------------*/
  public static TechnologySkill createTestEntity() {
    return createTestEntity("Programming languages", Arrays.asList("Java", "TypeScript", "Python"));
  }

  public static TechnologySkill createTestEntity(String category, List<String> skillNames) {
    TechnologySkill technologySkill = new TechnologySkill();
    technologySkill.setCategory(category);
    technologySkill.setSkillNames(skillNames);
    technologySkill.setUserId(UserTestUtil.USER_ID.toString());

    return technologySkill;
  }

  /*---------------TechnologySkillRequestDto----------------*/
  public static TechnologySkillRequestDto createProgrammingSkillRequestDto() {
    return TechnologySkillRequestDto.builder()
            .category("Programmierung")
            .skillNames(Arrays.asList("Java", "Spring / Spring Boot",
                    "Design Pattners", "REST und SOAP WebServices", "Git", "Maven", "Python"))
            .userId(UserTestUtil.USER_ID.toString())
            .build();

  }

  public static TechnologySkillRequestDto createTestsSkillRequestDto() {
    return TechnologySkillRequestDto.builder()
            .category("Tests")
            .skillNames(Arrays.asList("JUnit", "Mockito", "Rest Assured"))
            .userId(UserTestUtil.USER_ID.toString())
            .build();
  }

  /*-------------------TechnologySkillDto-------------------*/
}
