package com.wagner.mycv.web.controller;

import com.wagner.mycv.service.CertificationService;
import com.wagner.mycv.testutil.StubFactory;
import com.wagner.mycv.web.dto.CertificationDto;
import com.wagner.mycv.web.dto.ErrorResponse;
import com.wagner.mycv.web.dto.request.CertificationRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import java.util.*;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.*;

// ToDo Rest-Assured-Tests in ein separates Lern-Projekt ausgliedern
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class CertificationRestControllerIntegrationTest {

  private static       String URI;
  private static final String RESOURCE_PATH = "/rest/v1/certifications";
  private static final String RESOURCE_ID   = "1";

  @LocalServerPort
  private int port;

  @MockBean
  CertificationService certificationService;

  private CertificationDto certificationDto;
  private CertificationRequestDto certificationRequestDto;

  @PostConstruct
  void init() {
    URI = "http://localhost:" + port + RESOURCE_PATH;
  }

  @BeforeEach
  void setUp() {
    certificationDto        = StubFactory.testCertificationDto();
    certificationRequestDto = StubFactory.testCertificationRequestDto();
  }

  @Test
  void test_get_with_extract_whole_dto() {
    // when(certificationService.find(Long.valueOf(RESOURCE_ID))).thenReturn(Optional.of(certificationDto));

    // Entweder Response auf das DTO mappen und dann das DTO prüfen
    CertificationDto dto = get(URI + "/" + RESOURCE_ID)
            .then()
              .contentType(MediaType.APPLICATION_XML_VALUE)
              .statusCode(HttpStatus.OK.value())
            .extract()
              .as(CertificationDto.class);

    assertNotNull(dto);
    assertEquals(certificationDto, dto);
  }

  @Test
  void test_get_with_extract_values_directly_from_body_by_field_name() {
    when(certificationService.find(Long.valueOf(RESOURCE_ID))).thenReturn(Optional.of(certificationDto));

    // Alternativ können mit Hilfe der Feldnamen die Werte auch direkt aus dem Body gelesen werden
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
      .when()
        .get(URI + "/" + RESOURCE_ID)
      .then()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .statusCode(HttpStatus.OK.value())
        .body("name", equalTo(certificationDto.getName()))
        .body("dateOfAchievement", equalTo(certificationDto.getDateOfAchievement()))
        .body("certificate", equalTo(certificationDto.getCertificate()))
        .body("id", equalTo(1)) // doesn't work with certificationDto.getId()!
        .body("userId", equalTo(certificationDto.getUserId()));
  }

  @Test
  void test_get_with_extract_json_string() {
    when(certificationService.find(Long.valueOf(RESOURCE_ID))).thenReturn(Optional.of(certificationDto));

    // Der ganze Response kann auch als JSON-String extrahiert werden
    String responseAsString =
            given()
              .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
              .get(URI + "/" + RESOURCE_ID)
            .then()
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .statusCode(HttpStatus.OK.value())
            .extract()
              .asString();

    assertNotNull(responseAsString);
    assertFalse(responseAsString.isEmpty());
  }

  @Test
  void test_get_with_extract_single_json_field() {
    when(certificationService.find(Long.valueOf(RESOURCE_ID))).thenReturn(Optional.of(certificationDto));

    // Es ist auch möglich, nur ein einzelnes Feld zu extrahieren
    String userId =
            given()
              .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
              .get(URI + "/" + RESOURCE_ID)
            .then()
              .statusCode(HttpStatus.OK.value())
              .contentType(MediaType.APPLICATION_JSON_VALUE)
            .extract()
              .path("userId");

    assertNotNull(userId);
    assertFalse(userId.isEmpty());
    assertEquals(certificationDto.getUserId(), userId);
  }

  @Test
  @Disabled
  void getAll() {
    when(certificationService.findAll()).thenReturn(Arrays.asList(certificationDto));

    List dto =
            get(URI)
            .then()
              .contentType(MediaType.APPLICATION_XML_VALUE)
              .statusCode(HttpStatus.OK.value())
            .extract()
              .as(List.class);

    assertNotNull(dto);
    assertEquals(certificationDto, dto);
  }

  @Test
  void create_with_valid_request_should_return_201() {
    when(certificationService.create(any(CertificationRequestDto.class))).thenReturn(certificationDto);
    Map<String, String> request = certificationRequestDto.toMap();

    CertificationDto actualCertificationDto =
            given()
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .accept(MediaType.APPLICATION_JSON_VALUE)
              .body(request)
            .when()
              .post(URI)
            .then()
              .statusCode(HttpStatus.CREATED.value())
              .contentType(MediaType.APPLICATION_JSON_VALUE)
            .extract()
              .as(CertificationDto.class);

    assertNotNull(actualCertificationDto);
    assertEquals(certificationDto, actualCertificationDto);
  }

  @Test
  void create_with_invalid_request_should_return_400() {
    when(certificationService.create(any(CertificationRequestDto.class))).thenReturn(certificationDto);
    Map<String, String> request = new HashMap<>();
    request.put("name", "");
    request.put("dateOfAchievement", null);
    request.put("userId", "");

    ErrorResponse errorResponse =
      given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .accept(MediaType.APPLICATION_JSON_VALUE)
        .body(request)
      .when()
        .post(URI)
      .then()
        .statusCode(HttpStatus.BAD_REQUEST.value())
        .contentType(MediaType.APPLICATION_JSON_VALUE)
      .extract()
        .as(ErrorResponse.class);

    assertNotNull(errorResponse);
    assertEquals(4, errorResponse.getMessages().size());
  }

  @Test
  void update_an_existing_resource_should_return_200() {
    when(certificationService.update(anyLong(), any(CertificationRequestDto.class))).thenReturn(Optional.of(certificationDto));
    Map<String, String> request = certificationRequestDto.toMap();

    CertificationDto actualCertificationDto =
            given()
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .accept(MediaType.APPLICATION_JSON_VALUE)
              .body(request)
            .when()
              .put(URI + "/" + RESOURCE_ID)
            .then()
              .statusCode(HttpStatus.OK.value())
              .contentType(MediaType.APPLICATION_JSON_VALUE)
            .extract()
              .as(CertificationDto.class);

    assertNotNull(actualCertificationDto);
    assertEquals(certificationDto, actualCertificationDto);
  }

  @Test
  void update_a_not_existing_resource_should_return_404() {
    when(certificationService.update(anyLong(), any(CertificationRequestDto.class))).thenReturn(Optional.empty());
    Map<String, String> request = certificationRequestDto.toMap();

    given()
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .body(request)
    .when()
      .put(URI + "/" + RESOURCE_ID)
    .then()
      .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  void update_with_invalid_request_should_return_400() {
    when(certificationService.update(anyLong(), any(CertificationRequestDto.class))).thenReturn(Optional.empty());
    Map<String, String> request = new HashMap<>();
    request.put("name", "A valid name");
    request.put("dateOfAchievement", "");
    request.put("userId", null);

    ErrorResponse errorResponse =
            given()
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .accept(MediaType.APPLICATION_JSON_VALUE)
              .body(request)
            .when()
              .put(URI + "/" + RESOURCE_ID)
            .then()
              .statusCode(HttpStatus.BAD_REQUEST.value())
              .contentType(MediaType.APPLICATION_JSON_VALUE)
            .extract()
              .as(ErrorResponse.class);

    assertNotNull(errorResponse);
    assertEquals(4, errorResponse.getMessages().size());
  }

  @Test
  void delete_an_existing_resource_should_return_200() {
    when(certificationService.delete(anyLong())).thenReturn(true);

    given()
    .when()
      .delete(URI + "/" + RESOURCE_ID)
    .then()
      .statusCode(HttpStatus.OK.value());
  }

  @Test
  void delete_on_not_existing_resource_should_return_404() {
    when(certificationService.delete(anyLong())).thenReturn(false);

    given()
    .when()
      .delete(URI + "/" + RESOURCE_ID)
    .then()
      .statusCode(HttpStatus.NOT_FOUND.value());
  }
}