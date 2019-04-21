package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.TechnologySkill;

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


  /*-------------------TechnologySkillDto-------------------*/
}
