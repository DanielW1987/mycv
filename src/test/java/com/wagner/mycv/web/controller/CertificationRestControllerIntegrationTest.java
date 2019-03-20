package com.wagner.mycv.web.controller;

import com.wagner.mycv.service.CertificationService;
import com.wagner.mycv.testutil.StubFactory;
import com.wagner.mycv.web.dto.CertificationDto;
import com.wagner.mycv.web.dto.request.CertificationRequestDto;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.*;

// ToDo Rest-Assured-Tests in ein separates Lern-Projekt ausgliedern
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CertificationRestControllerIntegrationTest {

  private static       String URI;
  private static final String RESOURCE_PATH = "/rest/v1/certifications";
  private static final String RESOURCE_ID   = "1";

  @LocalServerPort
  private int port;

  @MockBean
  CertificationService certificationService;

  private CertificationDto certificationStub;
  private CertificationRequestDto certificationRequestDto;

  @PostConstruct
  void init() {
    URI = "http://localhost:" + port + RESOURCE_PATH;
  }

  @BeforeEach
  void setUp() {
    certificationStub = StubFactory.testCertificationDto();
    certificationRequestDto = StubFactory.testCertificationRequestDto();
  }

  @Test
  void test_get_with_extract_whole_dto() {
    when(certificationService.find(Long.valueOf(RESOURCE_ID))).thenReturn(Optional.of(certificationStub));

    // Entweder Response auf das DTO mappen und dann das DTO prüfen
    CertificationDto dto = get(URI + "/" + RESOURCE_ID)
            .then()
              .contentType(MediaType.APPLICATION_XML_VALUE)
              .statusCode(HttpStatus.OK.value())
            .extract()
              .as(CertificationDto.class);

    assertNotNull(dto);
    assertEquals(certificationStub, dto);
  }

  @Test
  void test_get_with_extract_values_directly_from_body_by_field_name() {
    when(certificationService.find(Long.valueOf(RESOURCE_ID))).thenReturn(Optional.of(certificationStub));

    // Alternativ können mit Hilfe der Feldnamen die Werte auch direkt aus dem Body gelesen werden
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
      .when()
        .get(URI + "/" + RESOURCE_ID)
      .then()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .statusCode(HttpStatus.OK.value())
        .body("name", equalTo(certificationStub.getName()))
        .body("dateOfAchievement", equalTo(certificationStub.getDateOfAchievement()))
        .body("certificate", equalTo(certificationStub.getCertificate()))
        .body("id", equalTo(1)) // doesn't work with serviceResponseCertification.getId()!
        .body("userId", equalTo(certificationStub.getUserId()));
  }

  @Test
  void test_get_with_extract_json_string() {
    when(certificationService.find(Long.valueOf(RESOURCE_ID))).thenReturn(Optional.of(certificationStub));

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
    when(certificationService.find(Long.valueOf(RESOURCE_ID))).thenReturn(Optional.of(certificationStub));

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
    assertEquals(certificationStub.getUserId(), userId);
  }

  @Test
  void getAll() {
  }

  @Test
  void test_create_should_work() {
    Map<String, String> request = certificationRequestDto.toMap();

    Response response =
            given()
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .accept(MediaType.APPLICATION_JSON_VALUE)
              .body(request)
            .when()
              .post(URI)
            .then()
              .statusCode(HttpStatus.CREATED.value())
              //.contentType(MediaType.APPLICATION_JSON_VALUE)
            .extract()
              .response();
// todo: der service ist nicht gemock, daher wird hier nichts zurückgebgen
    // extract generated id
    String name = response.jsonPath().getString("name");
    assertNotNull(name);
  }

  @Test
  void test_create_with_validation_errors() {

  }

  @Test
  void update() {
  }

  @Test
  void delete() {
  }
}