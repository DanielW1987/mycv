package com.wagner.mycv.service.impl;

import com.wagner.mycv.model.entity.Certification;
import com.wagner.mycv.model.repository.CertificationRepository;
import com.wagner.mycv.testutil.StubFactory;
import com.wagner.mycv.web.dto.CertificationDto;
import com.wagner.mycv.web.dto.request.CertificationRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CertificationServiceImplTest {

  private Certification testCertificationEntity;
  private CertificationRequestDto testCertificationRequestDto;

  @InjectMocks
  private CertificationServiceImpl certificationService;

  @Mock
  private CertificationRepository certificationRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    testCertificationEntity = StubFactory.testCertificationEntity();
    testCertificationRequestDto = StubFactory.testCertificationRequestDto();
  }

  @Test
  void test_findAll() {
    // fail("Not yet implemented");
  }

  @Test
  void test_find() {
    when(certificationRepository.findById(anyLong())).thenReturn(Optional.of(testCertificationEntity));

    Optional<CertificationDto> certificationDto = certificationService.find(1);

    assertTrue(certificationDto.isPresent());
    assertEquals(testCertificationEntity.getName(), certificationDto.get().getName());
    assertEquals(testCertificationEntity.getDateOfAchievement(), certificationDto.get().getDateOfAchievement());
    assertEquals(testCertificationEntity.getCertificate(), certificationDto.get().getCertificate());
    assertEquals(testCertificationEntity.getUserId(), certificationDto.get().getUserId());
    verify(certificationRepository, times(1)).findById(anyLong());
  }

  @Test
  void test_find_return_empty_optional() {
    when(certificationRepository.findById(anyLong())).thenReturn(Optional.empty());

    Optional<CertificationDto> certificationDto = certificationService.find(1);

    assertFalse(certificationDto.isPresent());
    verify(certificationRepository, times(1)).findById(anyLong());
  }

  @Test
  void test_create() {
    // return value doesn't matter in service implementation
    when(certificationRepository.save(any(Certification.class))).thenReturn(null);

    CertificationDto certificationDto = certificationService.create(testCertificationRequestDto);

    assertNotNull(certificationDto);
    assertEquals(testCertificationRequestDto.getName(), certificationDto.getName());
    assertEquals(testCertificationRequestDto.getDateOfAchievement(), certificationDto.getDateOfAchievement());
    assertEquals(testCertificationRequestDto.getCertificate(), certificationDto.getCertificate());
    assertEquals(testCertificationRequestDto.getUserId(), certificationDto.getUserId());
    verify(certificationRepository, times(1)).save(any(Certification.class));
  }
}