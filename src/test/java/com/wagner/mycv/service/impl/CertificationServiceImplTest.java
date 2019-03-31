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
    ocaCertificationEntity     = StubFactory.testCertificationEntity("OCA Certification", LocalDate.of(2017, 7, 1));
    ocpCertificationEntity     = StubFactory.testCertificationEntity("OCP Certification", LocalDate.of(2018, 3, 7));
    mtaCertificationEntity     = StubFactory.testCertificationEntity("MTA Certification", LocalDate.of(2016, 7, 7));
    ocaCertificationRequestDto = StubFactory.testCertificationRequestDto("OCA Certification", LocalDate.of(2017, 7, 1));
    ocpCertificationRequestDto = StubFactory.testCertificationRequestDto("OCP Certification", LocalDate.of(2018, 3, 7));
  }

  @Test
  void test_findAll() {
    // given
    List<Certification> certifications = Arrays.asList(ocpCertificationEntity, ocaCertificationEntity, mtaCertificationEntity);
    when(certificationRepository.findAll(any(Sort.class))).thenReturn(certifications);

    // act
    List<CertificationDto> actualCertificationList = certificationService.findAll();

    // assert
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

    // act
    Optional<CertificationDto> certificationDto = certificationService.find(1);

    // assert
    assertTrue(certificationDto.isPresent());
    assertThatDtoAndEntityAreEqual(ocaCertificationEntity, certificationDto.get());
    verify(certificationRepository, times(1)).findById(anyLong());
  }

  @Test
  void test_find_return_empty_optional() {
    // given
    when(certificationRepository.findById(anyLong())).thenReturn(Optional.empty());

    // act
    Optional<CertificationDto> certificationDto = certificationService.find(1);

    // assert
    assertFalse(certificationDto.isPresent());
    verify(certificationRepository, times(1)).findById(anyLong());
  }

  @Test
  void test_create() {
    // given
    when(certificationRepository.save(any(Certification.class))).thenReturn(ocaCertificationEntity);

    // act
    CertificationDto certificationDto = certificationService.create(ocaCertificationRequestDto);

    // assert
    assertNotNull(certificationDto);
    assertThatRequestDtoAndDtoAreEqual(ocaCertificationRequestDto, certificationDto);
    verify(certificationRepository, times(1)).save(any(Certification.class));
  }

  @Test
  void test_create_all() {
    // given
    when(certificationRepository.saveAll(any())).thenReturn(Arrays.asList(ocpCertificationEntity, ocaCertificationEntity));

    // act
    List<CertificationRequestDto> certificationRequestDtos = Arrays.asList(ocaCertificationRequestDto, ocpCertificationRequestDto);
    List<CertificationDto> certificationDto = certificationService.createAll(certificationRequestDtos);

    // assert
    assertEquals(2, certificationDto.size());
    verify(certificationRepository, times(1)).saveAll(any());

    for (int index = 0; index < certificationRequestDtos.size(); index++) {
      assertThatRequestDtoAndDtoAreEqual(certificationRequestDtos.get(index), certificationDto.get(index));
    }
  }

  @Test
  void test_update() {
    // when
    when(certificationRepository.save(any(Certification.class))).thenReturn(ocaCertificationEntity);
    when(certificationRepository.findById(1L)).thenReturn(Optional.of(ocaCertificationEntity));

    // act
    Optional<CertificationDto> certificationDto = certificationService.update(1, ocaCertificationRequestDto);

    // assert
    assertNotNull(certificationDto);
    assertTrue(certificationDto.isPresent());
    assertThatRequestDtoAndDtoAreEqual(ocaCertificationRequestDto, certificationDto.get());
  }

  @Test
  void test_update_not_existing_entity() {
    // when
    when(certificationRepository.save(any(Certification.class))).thenReturn(ocaCertificationEntity);
    when(certificationRepository.findById(1L)).thenReturn(Optional.empty());

    // act
    Optional<CertificationDto> certificationDto = certificationService.update(1, ocaCertificationRequestDto);

    // assert
    assertNotNull(certificationDto);
    assertFalse(certificationDto.isPresent());
  }

  @Test
  void test_delete_existing() {
    // when
    when(certificationRepository.existsById(1L)).thenReturn(true);

    // act
    boolean actual = certificationService.delete(1L);

    // assert
    assertTrue(actual);
  }

  @Test
  void test_delete_not_existing() {
    // when
    when(certificationRepository.existsById(1L)).thenReturn(false);

    // act
    boolean actual = certificationService.delete(1L);

    // assert
    assertFalse(actual);
  }

  private void assertThatDtoAndEntityAreEqual(Certification entity, CertificationDto dto) {
    assertEquals(entity.getName(), dto.getName());
    assertEquals(entity.getDateOfAchievement(), LocalDate.parse(dto.getDateOfAchievement()));
    assertEquals(entity.getCertificate(), dto.getCertificate());
    assertEquals(entity.getUserId(), dto.getUserId());
  }

  private void assertThatRequestDtoAndDtoAreEqual(CertificationRequestDto requestDto, CertificationDto dto) {
    assertEquals(requestDto.getName(), dto.getName());
    assertEquals(requestDto.getDateOfAchievement(), LocalDate.parse(dto.getDateOfAchievement()));
    assertEquals(requestDto.getCertificate(), dto.getCertificate());
    assertEquals(requestDto.getUserId(), dto.getUserId());
  }
}