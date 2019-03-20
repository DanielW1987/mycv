package com.wagner.mycv.web.controller;

import com.wagner.mycv.service.CertificationService;
import com.wagner.mycv.testutil.StubFactory;
import com.wagner.mycv.web.dto.CertificationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CertificationRestControllerTest {

  @Mock
  private CertificationService certificationService;

  @InjectMocks
  private CertificationRestController certificationRestController;

  private CertificationDto certificationDto;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    certificationDto = StubFactory.testCertificationDto();
  }

  @Test
  void test_get() {
    when(certificationService.find(anyLong())).thenReturn(Optional.of(certificationDto));

    ResponseEntity<CertificationDto> responseEntity = certificationRestController.get(1);

    assertNotNull(responseEntity.getBody());
    assertEquals(certificationDto.getId(), responseEntity.getBody().getId());
    assertEquals(certificationDto.getName(), responseEntity.getBody().getName());
    assertEquals(certificationDto.getDateOfAchievement(), responseEntity.getBody().getDateOfAchievement());
    assertEquals(200, responseEntity.getStatusCodeValue());
  }

  @Test
  void get_resource_not_found() {
    when(certificationService.find(anyLong())).thenReturn(Optional.empty());

    ResponseEntity<CertificationDto> responseEntity = certificationRestController.get(1);

    assertNull(responseEntity.getBody());
    assertEquals(404, responseEntity.getStatusCodeValue());
  }

  @Test
  void getAll() {
  }

  @Test
  void create() {
  }

  @Test
  void update() {
  }

  @Test
  void delete() {
  }
}