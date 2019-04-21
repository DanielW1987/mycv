package com.wagner.mycv.web.controller;

import com.wagner.mycv.model.exception.RestRequestValidationException;
import com.wagner.mycv.service.CertificationService;
import com.wagner.mycv.testutil.CertificationTestUtil;
import com.wagner.mycv.web.dto.CertificationDto;
import com.wagner.mycv.web.dto.request.CertificationRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CertificationsRestControllerTest {

  @Mock
  private CertificationService certificationService;

  @Mock
  private BindingResult bindingResult;

  @InjectMocks
  private CertificationsRestController certificationsRestController;

  private CertificationDto ocaCertificationDto;
  private CertificationDto ocpCertificationDto;
  private CertificationRequestDto certificationRequestDto;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    ocaCertificationDto     = CertificationTestUtil.testCertificationDto("OCA Certification","2017-07-01");
    ocpCertificationDto     = CertificationTestUtil.testCertificationDto("OCP Certification","2018-03-08");
    certificationRequestDto = CertificationTestUtil.createOcaCertificationRequestDto();
  }

  @Test
  void test_get() {
    // given
    when(certificationService.find(anyLong())).thenReturn(Optional.of(ocaCertificationDto));

    // when
    ResponseEntity<CertificationDto> responseEntity = certificationsRestController.get(1);

    // then
    assertNotNull(responseEntity);
    assertNotNull(responseEntity.getBody());
    assertEquals(ocaCertificationDto, responseEntity.getBody());
    assertEquals(200, responseEntity.getStatusCodeValue());
  }

  @Test
  void test_get_resource_not_found() {
    // given
    when(certificationService.find(anyLong())).thenReturn(Optional.empty());

    // when
    ResponseEntity<CertificationDto> responseEntity = certificationsRestController.get(1);

    // then
    assertNotNull(responseEntity);
    assertNull(responseEntity.getBody());
    assertEquals(404, responseEntity.getStatusCodeValue());
  }

  @Test
  void test_get_all() {
    // given
    when(certificationService.findAll()).thenReturn(Arrays.asList(ocaCertificationDto, ocpCertificationDto));

    // when
    ResponseEntity<List<CertificationDto>> responseEntity = certificationsRestController.getAll();

    // then
    assertNotNull(responseEntity);
    assertEquals(200, responseEntity.getStatusCodeValue());
    assertNotNull(responseEntity.getBody());
    assertEquals(Arrays.asList(ocaCertificationDto, ocpCertificationDto), responseEntity.getBody());
  }

  @Test
  void test_create_without_validation_errors() {
    // given
    when(certificationService.create(any(CertificationRequestDto.class))).thenReturn(ocaCertificationDto);
    when(bindingResult.hasErrors()).thenReturn(false);

    // when
    ResponseEntity<CertificationDto> responseEntity = certificationsRestController.create(certificationRequestDto, bindingResult);

    // then
    assertNotNull(responseEntity);
    assertEquals(201, responseEntity.getStatusCodeValue());
    assertNotNull(responseEntity.getBody());
    assertEquals(ocaCertificationDto, responseEntity.getBody());
  }

  @Test
  void test_create_with_validation_errors() {
    // given
    when(certificationService.create(any(CertificationRequestDto.class))).thenReturn(ocaCertificationDto);
    when(bindingResult.hasErrors()).thenReturn(true);

    // then
    assertThrows(RestRequestValidationException.class, () -> certificationsRestController.create(certificationRequestDto, bindingResult));
  }

  @Test
  void test_update_without_validation_errors() {
    // given
    when(certificationService.update(anyLong(), any(CertificationRequestDto.class))).thenReturn(Optional.of(ocaCertificationDto));
    when(bindingResult.hasErrors()).thenReturn(false);

    // when
    ResponseEntity<CertificationDto> responseEntity = certificationsRestController.update(1L, certificationRequestDto, bindingResult);

    // then
    assertNotNull(responseEntity);
    assertEquals(200, responseEntity.getStatusCodeValue());
    assertNotNull(responseEntity.getBody());
    assertEquals(ocaCertificationDto, responseEntity.getBody());
  }

  @Test
  void test_update_with_validation_errors() {
    // given
    when(certificationService.update(anyLong(), any(CertificationRequestDto.class))).thenReturn(Optional.of(ocaCertificationDto));
    when(bindingResult.hasErrors()).thenReturn(true);

    // then
    assertThrows(RestRequestValidationException.class, () -> certificationsRestController.update(1L, certificationRequestDto, bindingResult));
  }

  @Test
  void test_update_not_existing_resource() {
    // given
    when(certificationService.update(anyLong(), any(CertificationRequestDto.class))).thenReturn(Optional.empty());
    when(bindingResult.hasErrors()).thenReturn(false);

    // when
    ResponseEntity<CertificationDto> responseEntity = certificationsRestController.update(1L, certificationRequestDto, bindingResult);

    // then
    assertNotNull(responseEntity);
    assertEquals(404, responseEntity.getStatusCodeValue());
    assertNull(responseEntity.getBody());
  }

  @Test
  void test_delete_existing_resource() {
    // given
    when(certificationService.delete(anyLong())).thenReturn(true);

    // when
    ResponseEntity<Void> responseEntity = certificationsRestController.delete(1L);

    // then
    assertNotNull(responseEntity);
    assertEquals(200, responseEntity.getStatusCodeValue());
  }

  @Test
  void test_delete_not_existing_resource() {
    // given
    when(certificationService.delete(anyLong())).thenReturn(false);

    // when
    ResponseEntity<Void> responseEntity = certificationsRestController.delete(1L);

    // then
    assertNotNull(responseEntity);
    assertEquals(404, responseEntity.getStatusCodeValue());
  }
}