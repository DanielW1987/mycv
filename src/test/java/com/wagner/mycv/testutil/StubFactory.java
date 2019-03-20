package com.wagner.mycv.testutil;

import com.wagner.mycv.model.entity.*;
import com.wagner.mycv.web.dto.CertificationDto;
import com.wagner.mycv.web.dto.request.CertificationRequestDto;

import java.time.LocalDate;
import java.util.UUID;

public class StubFactory {

  private StubFactory() {
    // has only static methods
    // ToDo: Aktuell kann man für Testzwecke createDate, createdBy, lastModifiedDate und lastModifiedBy nicht setzen
    // Vielleicht sollte man das via Reflection machen?
  }

  public static Certification testCertificationEntity() {
    Certification certification = new Certification();
    certification.setName("Very cool certification");
    certification.setDateOfAchievement(LocalDate.of(2018, 1, 1));
    certification.setCertificate("certificate file");
    certification.setUserId(UUID.randomUUID().toString());

    return certification;
  }

  public static CertificationRequestDto testCertificationRequestDto() {
    CertificationRequestDto requestDto = new CertificationRequestDto();
    requestDto.setName("Very cool certification");
    requestDto.setDateOfAchievement(LocalDate.of(2018, 1, 1));
    requestDto.setCertificate("certificate file");
    requestDto.setUserId(UUID.randomUUID().toString());

    return requestDto;
  }

  public static CertificationDto testCertificationDto() {
    CertificationDto certificationDto = new CertificationDto();
    certificationDto.setId(1);
    certificationDto.setName("Very cool certification");
    certificationDto.setDateOfAchievement("2018-01-01");
    certificationDto.setCertificate("certificate file");
    certificationDto.setUserId(UUID.randomUUID().toString());
    certificationDto.setCreatedBy("Administrator");
    certificationDto.setCreatedDate("2018-01-01");
    certificationDto.setLastModifiedBy("Administrator");
    certificationDto.setLastModifiedDate("2018-01-01");

    return certificationDto;
  }

  public static Education testEducation() {
    return null;
  }

  public static Language testLanguage() {
    return null;
  }

  public static PrivateProject testPrivateProject() {
    return null;
  }

  public static TechnologySkill testTechnologySkill() {
    return null;
  }

  public static WorkingExperience testWorkingExperience() {
    return null;
  }

}
