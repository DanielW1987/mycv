package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.Education;
import com.wagner.mycv.web.dto.CertificationDto;
import com.wagner.mycv.web.dto.EducationDto;
import com.wagner.mycv.web.dto.request.EducationRequestDto;

import java.time.LocalDate;
import java.util.UUID;

public class EducationTestUtil {

  private EducationTestUtil() {
    // has only static methods
  }

  /*--------------------Education---------------------*/
  public static Education createTestEntity() {
    return createTestEntity("HTW Berlin", "Master of Science");
  }

  public static Education createTestEntity(String facility, String graduation) {
    Education education = new Education();
    education.setBegin(LocalDate.of(2017, 1, 1));
    education.setEnd(LocalDate.of(2019, 1, 1));
    education.setFacility(facility);
    education.setGraduation(graduation);
    education.setUserId(UserTestUtil.USER_ID);

    return education;
  }

  /*---------------EducationRequestDto----------------*/
  public static EducationRequestDto createBachelorEducationRequestDto() {
    return EducationRequestDto.builder()
            .facility("Hochschule für Technik und Wirtschaft Berlin")
            .begin(LocalDate.of(2008, 10, 1))
            .end(LocalDate.of(2011, 9, 30))
            .graduation("B. Sc. Wirtschaftsinformatik")
            .build();
  }

  public static EducationRequestDto createMasterEducationRequestDto() {
    return EducationRequestDto.builder()
            .facility("Hochschule für Technik und Wirtschaft Berlin")
            .begin(LocalDate.of(2011, 10, 1))
            .end(LocalDate.of(2013, 9, 30))
            .graduation("M. Sc. Wirtschaftsinformatik")
            .build();
  }

  /*-------------------EducationDto-------------------*/
  public static EducationDto createHighschoolEducationDto() {
    EducationDto bachelorDto = new EducationDto();
    bachelorDto.setId(1);
    bachelorDto.setFacility("Berufliche Schule des Landkreises Ludwigslust");
    bachelorDto.setGraduation("Allgemeine Hochschulreife und Berufsausbildung (kfm. Assistent für Informationsverarbeitung)");
    bachelorDto.setBegin(LocalDate.of(2014, 9, 1));
    bachelorDto.setEnd(LocalDate.of(2008, 7, 31));
    bachelorDto.setUserId(UserTestUtil.USER_ID);
    bachelorDto.setCreatedBy("Administrator");
    bachelorDto.setCreatedDate(LocalDate.now().toString());
    bachelorDto.setLastModifiedBy("Administrator");
    bachelorDto.setLastModifiedDate(LocalDate.now().toString());

    return bachelorDto;
  }
}
