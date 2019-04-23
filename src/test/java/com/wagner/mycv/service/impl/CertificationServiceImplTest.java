package com.wagner.mycv.service.impl;

import com.wagner.mycv.model.entity.Certification;
import com.wagner.mycv.model.repository.CertificationRepository;
import com.wagner.mycv.testutil.CertificationTestUtil;
import com.wagner.mycv.web.dto.CertificationDto;
import com.wagner.mycv.web.dto.request.CertificationRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CertificationServiceImplTest {

  private Certification ocaCertificationEntity;
  private Certification ocpCertificationEntity;
  private Certification mtaCertificationEntity;

  private CertificationRequestDto ocaCertificationRequestDto;
  private CertificationRequestDto ocpCertificationRequestDto;

  @InjectMocks
  private CertificationServiceImpl certificationService;

  @Mock
  private CertificationRepository certificationRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    ocaCertificationEntity     = CertificationTestUtil.createTestEntity("OCA Certification", LocalDate.of(2017, 7, 1));
    ocpCertificationEntity     = CertificationTestUtil.createTestEntity("OCP Certification", LocalDate.of(2018, 3, 7));
    mtaCertificationEntity     = CertificationTestUtil.createTestEntity("MTA Certification", LocalDate.of(2016, 7, 7));
    ocaCertificationRequestDto = CertificationTestUtil.createOcaCertificationRequestDto();
    ocpCertificationRequestDto = CertificationTestUtil.createOcpCertificationRequestDto();
  }

  @Test
  void test_findAll() {
    // given
    List<Certification> certifications = Arrays.asList(ocpCertificationEntity, ocaCertificationEntity, mtaCertificationEntity);
    when(certificationRepository.findAll(any(Sort.class))).thenReturn(certifications);

    // when
    List<CertificationDto> actualCertificationList = certificationService.findAll();

    // then
    assertEquals(3, actualCertificationList.size());
    verify(certificationRepository, times(1)).findAll(any(Sort.class));

    List<CertificationDto> expectedCertificationList = certifications.stream()
            .map(entity -> new ModelMapper().map(entity, CertificationDto.class))
            .collect(Collectors.toList());

    for (int index = 0; index < expectedCertificationList.size(); index++) {
      assertEquals(expectedCertificationList.get(index), actualCertificationList.get(index));
    }
  }

  @Test
  void test_find() {
    // given
    when(certificationRepository.findById(anyLong())).thenReturn(Optional.of(ocaCertificationEntity));

    // when
    Optional<CertificationDto> certificationDto = certificationService.find(1);

    // then
    assertTrue(certificationDto.isPresent());
    assertThatDtoAndEntityAreEqual(ocaCertificationEntity, certificationDto.get());
    verify(certificationRepository, times(1)).findById(anyLong());
  }

  @Test
  void test_find_return_empty_optional() {
    // given
    when(certificationRepository.findById(anyLong())).thenReturn(Optional.empty());

    // when
    Optional<CertificationDto> certificationDto = certificationService.find(1);

    // then
    assertFalse(certificationDto.isPresent());
    verify(certificationRepository, times(1)).findById(anyLong());
  }

  @Test
  void test_create() {
    // given
    when(certificationRepository.save(any(Certification.class))).thenReturn(ocaCertificationEntity);

    // when
    CertificationDto certificationDto = certificationService.create(ocaCertificationRequestDto);

    // then
    assertNotNull(certificationDto);
    assertThatRequestDtoAndDtoAreEqual(ocaCertificationRequestDto, certificationDto);
    verify(certificationRepository, times(1)).save(any(Certification.class));
  }

  @Test
  void test_create_all() {
    // given
    when(certificationRepository.saveAll(any())).thenReturn(Arrays.asList(ocpCertificationEntity, ocaCertificationEntity));

    // when
    List<CertificationRequestDto> certificationRequestDtos = Arrays.asList(ocaCertificationRequestDto, ocpCertificationRequestDto);
    List<CertificationDto> certificationDto = certificationService.createAll(certificationRequestDtos);

    // then
    assertEquals(2, certificationDto.size());
    verify(certificationRepository, times(1)).saveAll(any());

    for (int index = 0; index < certificationRequestDtos.size(); index++) {
      assertThatRequestDtoAndDtoAreEqual(certificationRequestDtos.get(index), certificationDto.get(index));
    }
  }

  @Test
  void test_update() {
    // given
    when(certificationRepository.save(any(Certification.class))).thenReturn(ocaCertificationEntity);
    when(certificationRepository.findById(1L)).thenReturn(Optional.of(ocaCertificationEntity));

    // when
    Optional<CertificationDto> certificationDto = certificationService.update(1, ocaCertificationRequestDto);

    // then
    assertNotNull(certificationDto);
    assertTrue(certificationDto.isPresent());
    assertThatRequestDtoAndDtoAreEqual(ocaCertificationRequestDto, certificationDto.get());
  }

  @Test
  void test_update_not_existing_entity() {
    // given
    when(certificationRepository.findById(1L)).thenReturn(Optional.empty());

    // when
    Optional<CertificationDto> certificationDto = certificationService.update(1, ocaCertificationRequestDto);

    // then
    assertNotNull(certificationDto);
    assertFalse(certificationDto.isPresent());
  }

  @Test
  void test_delete_existing() {
    // given
    when(certificationRepository.existsById(1L)).thenReturn(true);

    // when
    boolean actual = certificationService.delete(1L);

    // then
    assertTrue(actual);
  }

  @Test
  void test_delete_not_existing() {
    // given
    when(certificationRepository.existsById(1L)).thenReturn(false);

    // when
    boolean actual = certificationService.delete(1L);

    // then
    assertFalse(actual);
  }

  private void assertThatDtoAndEntityAreEqual(Certification entity, CertificationDto dto) {
    assertEquals(entity.getName(), dto.getName());
    assertEquals(entity.getDateOfAchievement(), dto.getDateOfAchievement());
    assertEquals(entity.getCertificate(), dto.getCertificate());
    assertEquals(entity.getUserId(), dto.getUserId());
  }

  private void assertThatRequestDtoAndDtoAreEqual(CertificationRequestDto requestDto, CertificationDto dto) {
    assertEquals(requestDto.getName(), dto.getName());
    assertEquals(requestDto.getDateOfAchievement(), dto.getDateOfAchievement());
    assertEquals(requestDto.getCertificate(), dto.getCertificate());
  }
}