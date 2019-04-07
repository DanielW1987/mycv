package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.*;
import com.wagner.mycv.web.dto.CertificationDto;
import com.wagner.mycv.web.dto.request.CertificationRequestDto;

import java.time.LocalDate;
import java.util.UUID;

public class CertificationTestUtil {

  private CertificationTestUtil() {
    // has only static methods
    // ToDo: Aktuell kann man für Testzwecke createDate, createdBy, lastModifiedDate und lastModifiedBy nicht setzen
    // Vielleicht sollte man das via Reflection machen?
  }

  /*--------------------Certification---------------------*/
  public static Certification createTestCertificationEntity() {
    return createTestCertificationEntity("Very cool vertification", LocalDate.of(2018, 1, 1));
  }

  public static Certification createTestCertificationEntity(String name, LocalDate dateOfAchievement) {
    Certification certification = new Certification();
    certification.setName(name);
    certification.setDateOfAchievement(dateOfAchievement);
    certification.setCertificate("certification file");
    certification.setUserId(UUID.randomUUID().toString());

    return certification;
  }

  /*--------------------CertificationRequestDto---------------------*/
  public static CertificationRequestDto createTestCertificationRequestDto() {
    return createTestCertificationRequestDto("Very cool certification", LocalDate.of(2019, 1, 1));
  }

  public static CertificationRequestDto createMtaCertificationRequestDto() {
    return createTestCertificationRequestDto("Microsoft Technology Associate: Database Fundamentals",
            LocalDate.of(2014, 10, 1));
  }

  public static CertificationRequestDto createOcaCertificationRequestDto() {
    return createTestCertificationRequestDto("Oracle Certified Associate, Java SE 8 Programmer I",
            LocalDate.of(2017, 7, 1));
  }

  public static CertificationRequestDto createOcpCertificationRequestDto() {
    return createTestCertificationRequestDto("Oracle Certified Professional, Java SE 8 Programmer II",
            LocalDate.of(2018, 3, 1));
  }

  private static CertificationRequestDto createTestCertificationRequestDto(String name, LocalDate dateOfAchievement) {
    return CertificationRequestDto.builder()
            .name(name)
            .dateOfAchievement(dateOfAchievement)
            .certificate("certification file")
            .userId(UserTestUtil.USER_ID.toString())
            .build();
  }

  /*--------------------CertificationDto---------------------*/
  public static CertificationDto createMtaCertificationDto() {
    CertificationDto certificationDto = new CertificationDto();
    certificationDto.setId(1);
    certificationDto.setName("Microsoft Technology Associate: Database Fundamentals");
    certificationDto.setDateOfAchievement("2014-10-01");
    certificationDto.setCertificate("certification file");
    certificationDto.setUserId(UserTestUtil.USER_ID.toString());
    certificationDto.setCreatedBy("Administrator");
    certificationDto.setCreatedDate(LocalDate.now().toString());
    certificationDto.setLastModifiedBy("Administrator");
    certificationDto.setLastModifiedDate(LocalDate.now().toString());

    return certificationDto;
  }

  public static CertificationDto testCertificationDto() {
    return testCertificationDto("Very cool certification", "2018-01-01");
  }

  public static CertificationDto testCertificationDto(String name, String dateOfAchievement) {
    CertificationDto certificationDto = new CertificationDto();
    certificationDto.setId(1);
    certificationDto.setName(name);
    certificationDto.setDateOfAchievement(dateOfAchievement);
    certificationDto.setCertificate("certification file");
    certificationDto.setUserId(UserTestUtil.USER_ID.toString());
    certificationDto.setCreatedBy("Administrator");
    certificationDto.setCreatedDate("2018-01-01");
    certificationDto.setLastModifiedBy("Administrator");
    certificationDto.setLastModifiedDate("2018-01-01");

    return certificationDto;
  }
}