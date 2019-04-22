package com.wagner.mycv.web.controller;

import com.wagner.mycv.service.LanguageService;
import com.wagner.mycv.testutil.LanguageTestUtil;
import com.wagner.mycv.testutil.UserTestUtil;
import com.wagner.mycv.web.dto.ErrorResponse;
import com.wagner.mycv.web.dto.LanguageDto;
import com.wagner.mycv.web.dto.request.LanguageRequestDto;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class LanguagesRestControllerIntegrationTest {

  private static       String URI;
  private static final String RESOURCE_PATH              = "/rest/v1/languages";
  private static final String RESOURCE_ID                = "1";
  private static final String NOT_EXISTING_RESOURCE_ID   = "99999";

  @Value("${server.address}")
  private String serverAddress;

  @LocalServerPort
  private int port;

  private LanguageDto germanLanguageDto;
  private LanguageRequestDto languageRequestDto;

  @Autowired
  private LanguageService languageService;

  @PostConstruct
  void init() {
    URI = "http://" + serverAddress + ":" + port + RESOURCE_PATH;
  }

  @BeforeEach
  void setUp() {
    germanLanguageDto  = LanguageTestUtil.createGermanLanguageDto();
    languageRequestDto = LanguageTestUtil.createGermanLanguageRequestDto();
  }

  @Test
  void test_get_with_extract_whole_dto() {
    LanguageDto dto =
            given()
              .accept(MediaType.APPLICATION_JSON_VALUE)
              .pathParam("id", RESOURCE_ID)
            .when()
              .get(URI + "/{id}")
            .then()
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .statusCode(HttpStatus.OK.value())
              .extract()
              .as(LanguageDto.class);

    // restassured tries to unmarshal LocalDate values via the default constructor of LocalDate if the requested content type is XML.
    // LocalDate has no default constructor and so this ends in an NoSuchMethodError.

    assertNotNull(dto);
    assertEquals(germanLanguageDto, dto);
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

    LanguageDto[] languageDtos = response.getBody().as(LanguageDto[].class);
    assertEquals(3, languageDtos.length);

    // assert that all fields have a value
    Stream.of(languageDtos).forEach(languageDto -> {
      assertTrue(languageDto.getId() != 0);
      assertNotNull(languageDto.getName());
      assertTrue(languageDto.getLevel() != 0);
      assertNotNull(languageDto.getUserId());
      assertNotNull(languageDto.getCreatedDate());
      assertNotNull(languageDto.getCreatedBy());
      assertNotNull(languageDto.getLastModifiedDate());
      assertNotNull(languageDto.getLastModifiedBy());
    });
  }

  @Test
  void create_with_valid_request_should_return_201() {
    Map<String, String> request = languageRequestDto.toMap();

    LanguageDto createdLanguage =
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
              .as(LanguageDto.class);

    assertNotNull(createdLanguage);
    assertEquals(languageRequestDto.getUserId(), createdLanguage.getUserId());
    assertEquals(languageRequestDto.getName(), createdLanguage.getName());
    assertEquals(languageRequestDto.getLevel(), createdLanguage.getLevel());
    assertTrue(createdLanguage.getId() != 0);
    assertEquals("Administrator", createdLanguage.getCreatedBy());
    assertEquals(LocalDate.now().toString(), createdLanguage.getCreatedDate());
    assertEquals("Administrator", createdLanguage.getLastModifiedBy());
    assertEquals(LocalDate.now().toString(), createdLanguage.getLastModifiedDate());

    // remove created certification
    languageService.delete(createdLanguage.getId());
  }

  @Test
  void create_with_invalid_request_should_return_400() {
    Map<String, String> request = new HashMap<>();
    request.put("name", " ");
    request.put("level", "150");
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
    // create a new language which can be updated
    LanguageRequestDto testLanguageRequest = LanguageRequestDto.builder()
            .name("update language")
            .level((byte) 10)
            .userId(UserTestUtil.USER_ID.toString())
            .build();

    LanguageDto testLanguage = languageService.create(testLanguageRequest);

    LanguageDto updatedLanguageDto =
            given()
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .accept(MediaType.APPLICATION_JSON_VALUE)
              .body(languageRequestDto.toMap())
            .when()
              .pathParam("id", testLanguage.getId())
              .put(URI + "/{id}")
            .then()
              .statusCode(HttpStatus.OK.value())
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .extract()
              .as(LanguageDto.class);

    assertNotNull(updatedLanguageDto);
    assertEquals(languageRequestDto.getName(), updatedLanguageDto.getName());
    assertEquals(languageRequestDto.getLevel(), updatedLanguageDto.getLevel());

    // id and user id should not be updated
    assertEquals(testLanguage.getId(), updatedLanguageDto.getId());
    assertEquals(testLanguage.getUserId(), updatedLanguageDto.getUserId());

    // remove created testCertification
    languageService.delete(testLanguage.getId());
  }

  @Test
  void update_a_not_existing_resource_should_return_404() {
    Map<String, String> request = languageRequestDto.toMap();

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
    request.put("name", null);
    request.put("level", "-10");
    request.put("userId", "");

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
    // create a new language which can be deleted
    LanguageDto testLanguage = languageService.create(languageRequestDto);

    given()
    .when()
      .pathParam("id", testLanguage.getId())
      .delete(URI + "/{id}")
    .then()
     .statusCode(HttpStatus.OK.value());

    assertFalse(languageService.find(testLanguage.getId()).isPresent());
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
