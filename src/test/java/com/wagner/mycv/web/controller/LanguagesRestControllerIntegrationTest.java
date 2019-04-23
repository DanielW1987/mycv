package com.wagner.mycv.web.controller;

import com.wagner.mycv.service.LanguageService;
import com.wagner.mycv.testutil.LanguageTestUtil;
import com.wagner.mycv.utils.RestAssuredRequestHandler;
import com.wagner.mycv.web.dto.ErrorResponse;
import com.wagner.mycv.web.dto.LanguageDto;
import com.wagner.mycv.web.dto.request.LanguageRequestDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class LanguagesRestControllerIntegrationTest {

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

  private RestAssuredRequestHandler requestHandler;

  @BeforeEach
  void setUp() {
    String requestUri  = "http://" + serverAddress + ":" + port + RESOURCE_PATH;
    requestHandler     = new RestAssuredRequestHandler(requestUri);
    germanLanguageDto  = LanguageTestUtil.createGermanLanguageDto();
    languageRequestDto = LanguageTestUtil.createGermanLanguageRequestDto();
  }

  @Test
  void test_get() {
    ValidatableResponse response = requestHandler.doGet(ContentType.JSON, RESOURCE_ID);

    // assert
    response.contentType(ContentType.JSON)
            .statusCode(HttpStatus.OK.value());

    LanguageDto responseDto = response.extract().as(LanguageDto.class);

    assertNotNull(responseDto);
    assertEquals(germanLanguageDto.getId(), responseDto.getId());
    assertEquals(germanLanguageDto.getName(), responseDto.getName());
    assertEquals(germanLanguageDto.getLevel(), responseDto.getLevel());
    assertEquals(germanLanguageDto.getUserId(), responseDto.getUserId());
  }

  @Test
  void get_on_not_existing_resource_should_return_404() {
    ValidatableResponse validatableResponse = requestHandler.doGet(ContentType.JSON, NOT_EXISTING_RESOURCE_ID);

    // assert
    validatableResponse.statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  void getAll() {
    ValidatableResponse validatableResponse = requestHandler.doGetAll(ContentType.JSON);

    // assert
    validatableResponse.contentType(ContentType.JSON)
                       .statusCode(HttpStatus.OK.value());

    Response response = validatableResponse.extract().response();

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
    ValidatableResponse validatableResponse = requestHandler.doPost(ContentType.JSON, request);
    LanguageDto         createdLanguage     = validatableResponse.extract().as(LanguageDto.class);

    // assert
    validatableResponse.statusCode(HttpStatus.CREATED.value())
                       .contentType(ContentType.JSON);

    assertNotNull(createdLanguage);
    assertNotNull(createdLanguage.getUserId());
    assertEquals(languageRequestDto.getName(), createdLanguage.getName());
    assertEquals(languageRequestDto.getLevel(), createdLanguage.getLevel());
    assertTrue(createdLanguage.getId() != 0);
    assertEquals("Administrator", createdLanguage.getCreatedBy());
    assertEquals(LocalDate.now().toString(), createdLanguage.getCreatedDate());
    assertEquals("Administrator", createdLanguage.getLastModifiedBy());
    assertEquals(LocalDate.now().toString(), createdLanguage.getLastModifiedDate());

    // remove created language
    languageService.delete(createdLanguage.getId());
  }

  @Test
  void create_with_invalid_request_should_return_400() {
    Map<String, String> request = new HashMap<>();
    request.put("name", " ");
    request.put("level", "150");

    ValidatableResponse validatableResponse = requestHandler.doPost(ContentType.JSON, request);
    ErrorResponse       errorResponse       = validatableResponse.extract().as(ErrorResponse.class);

    // assert
    validatableResponse.statusCode(HttpStatus.BAD_REQUEST.value())
                       .contentType(ContentType.JSON);

    assertNotNull(errorResponse);
    assertEquals(3, errorResponse.getMessages().size());
  }

  @Test
  void update_an_existing_resource_should_return_200() {
    // create a new language which can be updated
    LanguageRequestDto testLanguageRequest = LanguageRequestDto.builder()
            .name("update language")
            .level((byte) 10)
            .build();

    LanguageDto testLanguage = languageService.create(testLanguageRequest);

    Map<String, String> request             = languageRequestDto.toMap();
    String              resourceId          = Long.toString(testLanguage.getId());
    ValidatableResponse validatableResponse = requestHandler.doPut(ContentType.JSON, request, resourceId);
    LanguageDto         updatedLanguageDto  = validatableResponse.extract().as(LanguageDto.class);

    // assert
    validatableResponse.statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON);

    assertNotNull(updatedLanguageDto);
    assertEquals(languageRequestDto.getName(), updatedLanguageDto.getName());
    assertEquals(languageRequestDto.getLevel(), updatedLanguageDto.getLevel());

    // id and user id should not be updated
    assertEquals(testLanguage.getId(), updatedLanguageDto.getId());
    assertEquals(testLanguage.getUserId(), updatedLanguageDto.getUserId());

    // remove created language
    languageService.delete(testLanguage.getId());
  }

  @Test
  void update_a_not_existing_resource_should_return_404() {
    ValidatableResponse validatableResponse = requestHandler.doPut(ContentType.JSON, languageRequestDto.toMap(), NOT_EXISTING_RESOURCE_ID);

    // assert
    validatableResponse.statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  void update_with_invalid_request_should_return_400() {
    Map<String, String> request = new HashMap<>();
    request.put("name", null);
    request.put("level", "-10");

    ValidatableResponse validatableResponse = requestHandler.doPut(ContentType.JSON, request, RESOURCE_ID);
    ErrorResponse       errorResponse       = validatableResponse.extract().as(ErrorResponse.class);

    // assert
    validatableResponse.statusCode(HttpStatus.BAD_REQUEST.value())
                       .contentType(ContentType.JSON);

    assertNotNull(errorResponse);
    assertEquals(3, errorResponse.getMessages().size());
  }

  @Test
  void delete_an_existing_resource_should_return_200() {
    // create a new language which can be deleted
    LanguageDto testLanguage = languageService.create(languageRequestDto);

    ValidatableResponse validatableResponse = requestHandler.doDelete(Long.toString(testLanguage.getId()));

    // assert
    validatableResponse.statusCode(HttpStatus.OK.value());
    assertFalse(languageService.find(testLanguage.getId()).isPresent());
  }

  @Test
  void delete_on_not_existing_resource_should_return_404() {
    ValidatableResponse validatableResponse = requestHandler.doDelete(NOT_EXISTING_RESOURCE_ID);

    // assert
    validatableResponse.statusCode(HttpStatus.NOT_FOUND.value());
  }
}
