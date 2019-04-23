package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.TechnologySkill;
import com.wagner.mycv.web.dto.TechnologySkillDto;
import com.wagner.mycv.web.dto.request.TechnologySkillRequestDto;

import java.time.LocalDate;
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
    technologySkill.setUserId(UserTestUtil.USER_ID);

    return technologySkill;
  }

  /*---------------TechnologySkillRequestDto----------------*/
  public static TechnologySkillRequestDto createProgrammingSkillRequestDto() {
    return TechnologySkillRequestDto.builder()
            .category("Programmierung")
            .skillNames(Arrays.asList("Java", "Spring / Spring Boot",
                    "Design Pattners", "REST und SOAP WebServices", "Git", "Maven", "Python"))
            .build();

  }

  public static TechnologySkillRequestDto createTestsSkillRequestDto() {
    return TechnologySkillRequestDto.builder()
            .category("Tests")
            .skillNames(Arrays.asList("JUnit", "Mockito", "Rest Assured"))
            .build();
  }

  /*-------------------TechnologySkillDto-------------------*/
  public static TechnologySkillDto createProgrammingTechnologySkillDto() {
    TechnologySkillDto dto = new TechnologySkillDto();
    dto.setId(1);
    dto.setCategory("Programmierung");
    dto.setSkillNames(Arrays.asList("Java", "Spring / Spring Boot",
            "Design Pattners", "REST und SOAP WebServices", "Git", "Maven", "Python"));
    dto.setUserId(UserTestUtil.USER_ID);
    dto.setCreatedBy("Administrator");
    dto.setCreatedDate(LocalDate.now().toString());
    dto.setLastModifiedBy("Administrator");
    dto.setLastModifiedDate(LocalDate.now().toString());

    return dto;
  }
}
