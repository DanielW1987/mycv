package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.Language;
import com.wagner.mycv.web.dto.request.LanguageRequestDto;

public class LanguageTestUtil {

  private LanguageTestUtil() {
    // has only statice methods
  }

  /*--------------------Language---------------------*/
  public static Language createTestEntity() {
    return createTestEntity("Deutsch", (byte) 80);
  }

  public static Language createTestEntity(String name, byte level) {
    Language language = new Language();
    language.setName(name);
    language.setLevel(level);
    language.setUserId(UserTestUtil.USER_ID.toString());

    return language;
  }

  /*---------------LanguageRequestDto----------------*/
  public static LanguageRequestDto createGermanLanguageRequestDto() {
    return LanguageRequestDto.builder()
            .name("Deutsch")
            .level((byte) 100)
            .userId(UserTestUtil.USER_ID.toString())
            .build();
  }

  public static LanguageRequestDto createEnglishLanguageRequestDto() {
    return LanguageRequestDto.builder()
            .name("Englisch")
            .level((byte) 60)
            .userId(UserTestUtil.USER_ID.toString())
            .build();
  }

  /*-------------------LanguageDto-------------------*/
}
