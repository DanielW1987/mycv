package com.wagner.mycv;

import com.wagner.mycv.service.CertificationService;
import com.wagner.mycv.testutil.CertificationTestUtil;
import com.wagner.mycv.testutil.UserTestUtil;
import com.wagner.mycv.web.dto.CertificationDto;
import com.wagner.mycv.web.dto.ErrorResponse;
import com.wagner.mycv.web.dto.request.CertificationRequestDto;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.*;

// ToDo Rest-Assured-Tests in ein separates Lern-Projekt ausgliedern
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class CertificationRestApiIntegrationTest {

  private static       String URI;
  private static final String RESOURCE_PATH              = "/rest/v1/certifications";
  private static final String RESOURCE_ID                = "1";
  private static final String NOT_EXISTING_RESOURCE_ID   = "99999";

  @LocalServerPort
  private int port;

  private CertificationDto        mtaCertificationDto;
  private CertificationRequestDto certificationRequestDto;

  @Autowired
  private CertificationService certificationService;

  @PostConstruct
  void init() {
    URI = "http://localhost:" + port + RESOURCE_PATH;
  }

  @BeforeEach
  void setUp() {
    mtaCertificationDto     = CertificationTestUtil.createMtaCertificationDto();
    certificationRequestDto = CertificationTestUtil.createTestCertificationRequestDto();
  }

  @Test
  void test_get_with_extract_whole_dto() {
    // Entweder Response auf das DTO mappen und dann das DTO prüfen
    CertificationDto dto =
            given()
              .pathParam("id", RESOURCE_ID)
            .when()
              .get(URI + "/{id}")
            .then()
              .contentType(MediaType.APPLICATION_XML_VALUE)
              .statusCode(HttpStatus.OK.value())
            .extract()
              .as(CertificationDto.class);

    assertNotNull(dto);
    assertEquals(mtaCertificationDto, dto);
  }

  @Test
  void test_get_with_extract_values_directly_from_body_by_field_name() {
    // Alternativ können mit Hilfe der Feldnamen die Werte auch direkt aus dem Body gelesen werden
    given()
        .accept(MediaType.APPLICATION_JSON_VALUE)
      .when()
        .pathParam("id", RESOURCE_ID)
        .get(URI + "/{id}")
      .then()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .statusCode(HttpStatus.OK.value())
        .body("name", equalTo(mtaCertificationDto.getName()))
        .body("dateOfAchievement", equalTo(mtaCertificationDto.getDateOfAchievement()))
        .body("certificate", equalTo(mtaCertificationDto.getCertificate()))
        .body("id", equalTo(1)) // doesn't work with mtaCertificationDto.getId()!
        .body("userId", equalTo(mtaCertificationDto.getUserId()));
  }

  @Test
  void test_get_with_extract_json_string() {
    // Der ganze Response kann auch als JSON-String extrahiert werden
    String responseAsString =
            given()
              .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
              .pathParam("id", RESOURCE_ID)
              .get(URI + "/{id}")
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
    // Es ist auch möglich, nur ein einzelnes Feld zu extrahieren
    String userId =
            given()
              .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
              .pathParam("id", RESOURCE_ID)
              .get(URI + "/{id}")
            .then()
              .statusCode(HttpStatus.OK.value())
              .contentType(MediaType.APPLICATION_JSON_VALUE)
            .extract()
              .path("userId");

    assertNotNull(userId);
    assertFalse(userId.isEmpty());
    assertEquals(mtaCertificationDto.getUserId(), userId);
  }

  @Test
  void get_on_not_existing_resource_should_return_404() {
    given()
    .when()
      .pathParam("id", NOT_EXISTING_RESOURCE_ID)
      .get(URI + "/{id}")
    .then()
      .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  void getAll() {
    Response response =
            given()
             .accept(MediaType.APPLICATION_JSON_VALUE)
            .when()
              .get(URI)
            .then()
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .statusCode(HttpStatus.OK.value())
            .extract()
              .response();

    assertNotNull(response);
    assertNotNull(response.getBody());

    CertificationDto[] certificationDtos = response.getBody().as(CertificationDto[].class);
    assertEquals(3, certificationDtos.length);

    // assert that all fields have a value
    Stream.of(certificationDtos).forEach(certificationDto -> {
      assertNotNull(certificationDto.getName());
      assertNotNull(certificationDto.getDateOfAchievement());
      assertNotNull(certificationDto.getCertificate());
      assertNotNull(certificationDto.getUserId());
      assertNotNull(certificationDto.getCreatedDate());
      assertNotNull(certificationDto.getCreatedBy());
      assertNotNull(certificationDto.getLastModifiedDate());
      assertNotNull(certificationDto.getLastModifiedBy());
    });

    // the ordering is from newest to oldest date of achievement
    LocalDate[] expected = new LocalDate[3];
    LocalDate[] actual   = new LocalDate[3];
    for (int index = 0; index < certificationDtos.length; index++) {
      actual[index] = LocalDate.parse(certificationDtos[index].getDateOfAchievement());
      expected[index] = actual[index];
    }

    Arrays.sort(expected, Comparator.reverseOrder());
    assertArrayEquals(expected, actual);
  }

  @Test
  void create_with_valid_request_should_return_201() {
    Map<String, String> request = certificationRequestDto.toMap();

    CertificationDto createdCertification =
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

    assertNotNull(createdCertification);
    assertEquals(certificationRequestDto.getUserId(), createdCertification.getUserId());
    assertEquals(certificationRequestDto.getName(), createdCertification.getName());
    assertEquals(certificationRequestDto.getDateOfAchievement().toString(), createdCertification.getDateOfAchievement());
    assertEquals(certificationRequestDto.getCertificate(), createdCertification.getCertificate());
    assertTrue(createdCertification.getId() > 0);
    assertEquals("Administrator", createdCertification.getCreatedBy());
    assertEquals(LocalDate.now().toString(), createdCertification.getCreatedDate());
    assertEquals("Administrator", createdCertification.getLastModifiedBy());
    assertEquals(LocalDate.now().toString(), createdCertification.getLastModifiedDate());

    // remove created certification
    certificationService.delete(createdCertification.getId());
  }

  @Test
  void create_with_invalid_request_should_return_400() {
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
    // create a new certification which can be updated
    CertificationRequestDto testCertificationRequest = CertificationRequestDto.builder()
            .name("Updated certification name")
            .dateOfAchievement(LocalDate.now())
            .certificate("certification file")
            .userId(UserTestUtil.USER_ID.toString())
            .build();

    CertificationDto testCertification = certificationService.create(testCertificationRequest);

    CertificationDto updatedCertificationDto =
            given()
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .accept(MediaType.APPLICATION_JSON_VALUE)
              .body(certificationRequestDto.toMap())
            .when()
              .pathParam("id", testCertification.getId())
              .put(URI + "/{id}")
            .then()
              .statusCode(HttpStatus.OK.value())
              .contentType(MediaType.APPLICATION_JSON_VALUE)
            .extract()
              .as(CertificationDto.class);

    assertNotNull(updatedCertificationDto);
    assertEquals(certificationRequestDto.getDateOfAchievement().toString(), updatedCertificationDto.getDateOfAchievement());
    assertEquals(certificationRequestDto.getName(), updatedCertificationDto.getName());
    assertEquals(certificationRequestDto.getCertificate(), updatedCertificationDto.getCertificate());
    assertEquals(testCertification.getId(), updatedCertificationDto.getId());
    assertEquals(testCertification.getUserId(), updatedCertificationDto.getUserId());

    // remove created testCertification
    certificationService.delete(testCertification.getId());
  }

  @Test
  void update_a_not_existing_resource_should_return_404() {
    Map<String, String> request = certificationRequestDto.toMap();

    given()
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .accept(MediaType.APPLICATION_JSON_VALUE)
      .body(request)
    .when()
      .pathParam("id", NOT_EXISTING_RESOURCE_ID)
      .put(URI + "/{id}")
    .then()
      .statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  void update_with_invalid_request_should_return_400() {
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
              .pathParam("id", RESOURCE_ID)
              .put(URI + "/{id}")
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
    // create a new certification which can be deleted
    CertificationDto testCertification = certificationService.create(certificationRequestDto);

    given()
    .when()
      .pathParam("id", testCertification.getId())
      .delete(URI + "/{id}")
    .then()
      .statusCode(HttpStatus.OK.value());

    assertFalse(certificationService.find(testCertification.getId()).isPresent());
  }

  @Test
  void delete_on_not_existing_resource_should_return_404() {
    given()
    .when()
      .pathParam("id", NOT_EXISTING_RESOURCE_ID)
      .delete(URI + "/{id}")
    .then()
      .statusCode(HttpStatus.NOT_FOUND.value());
  }
}