package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.Language;

public class LanguageTestUtil {

  private LanguageTestUtil() {
    // has only statice methods
  }

  /*--------------------Language---------------------*/
  public static Language createTestEntity() {
    return createTestEntity("German", (byte) 80);
  }

  public static Language createTestEntity(String name, byte level) {
    Language language = new Language();
    language.setName(name);
    language.setLevel(level);
    language.setUserId(UserTestUtil.USER_ID.toString());

    return language;
  }

  /*---------------LanguageRequestDto----------------*/


  /*-------------------LanguageDto-------------------*/
}
