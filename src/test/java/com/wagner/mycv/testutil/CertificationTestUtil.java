package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.*;
import com.wagner.mycv.web.dto.CertificationDto;
import com.wagner.mycv.web.dto.request.CertificationRequestDto;

import java.time.LocalDate;

public class CertificationTestUtil {

  private CertificationTestUtil() {
    // has only static methods
    // ToDo: Aktuell kann man für Testzwecke createDate, createdBy, lastModifiedDate und lastModifiedBy nicht setzen
    //  Vielleicht sollte man das via Reflection machen?
  }

  /*--------------------Certification---------------------*/
  public static Certification createTestEntity() {
    return createTestEntity("Very cool vertification", LocalDate.of(2018, 1, 1));
  }

  public static Certification createTestEntity(String name, LocalDate dateOfAchievement) {
    Certification certification = new Certification();
    certification.setName(name);
    certification.setDateOfAchievement(dateOfAchievement);
    certification.setCertificate("certification file");
    certification.setUserId(UserTestUtil.USER_ID);

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
            .build();
  }

  /*--------------------CertificationDto---------------------*/
  public static CertificationDto createMtaCertificationDto() {
    CertificationDto certificationDto = new CertificationDto();
    certificationDto.setId(1);
    certificationDto.setName("Microsoft Technology Associate: Database Fundamentals");
    certificationDto.setDateOfAchievement(LocalDate.of(2014, 10, 1));
    certificationDto.setCertificate("certification file");
    certificationDto.setUserId(UserTestUtil.USER_ID);
    certificationDto.setCreatedBy("Administrator");
    certificationDto.setCreatedDate(LocalDate.now());
    certificationDto.setLastModifiedBy("Administrator");
    certificationDto.setLastModifiedDate(LocalDate.now());

    return certificationDto;
  }

  public static CertificationDto testCertificationDto(String name, LocalDate dateOfAchievement) {
    CertificationDto certificationDto = new CertificationDto();
    certificationDto.setId(1);
    certificationDto.setName(name);
    certificationDto.setDateOfAchievement(dateOfAchievement);
    certificationDto.setCertificate("certification file");
    certificationDto.setUserId(UserTestUtil.USER_ID);
    certificationDto.setCreatedBy("Administrator");
    certificationDto.setCreatedDate(LocalDate.of(2018, 1, 1));
    certificationDto.setLastModifiedBy("Administrator");
    certificationDto.setLastModifiedDate(LocalDate.of(2018, 1, 1));

    return certificationDto;
  }
}