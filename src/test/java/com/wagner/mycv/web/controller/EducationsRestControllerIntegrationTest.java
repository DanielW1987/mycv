package com.wagner.mycv.web.controller;

import com.wagner.mycv.service.EducationService;
import com.wagner.mycv.testutil.EducationTestUtil;
import com.wagner.mycv.testutil.UserTestUtil;
import com.wagner.mycv.web.dto.EducationDto;
import com.wagner.mycv.web.dto.ErrorResponse;
import com.wagner.mycv.web.dto.request.EducationRequestDto;
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
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class EducationsRestControllerIntegrationTest {

  private static       String URI;
  private static final String RESOURCE_PATH              = "/rest/v1/educations";
  private static final String RESOURCE_ID                = "1";
  private static final String NOT_EXISTING_RESOURCE_ID   = "99999";

  @Value("${server.address}")
  private String serverAddress;

  @LocalServerPort
  private int port;

  private EducationDto bachelorEducationDto;
  private EducationRequestDto educationRequestDto;

  @Autowired
  private EducationService educationService;

  @PostConstruct
  void init() {
    URI = "http://" + serverAddress + ":" + port + RESOURCE_PATH;
  }

  @BeforeEach
  void setUp() {
    bachelorEducationDto = EducationTestUtil.createBachelorEducationDto();
    educationRequestDto  = EducationTestUtil.createBachelorEducationRequestDto();
  }

  @Test
  void test_get_with_extract_whole_dto() {
    EducationDto dto =
            given()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .pathParam("id", RESOURCE_ID)
                    .when()
                    .get(URI + "/{id}")
                    .then()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .statusCode(HttpStatus.OK.value())
                    .extract()
                    .as(EducationDto.class);

    // restassured tries to unmarshal LocalDate values via the default constructor of LocalDate if the requested content type is XML.
    // LocalDate has no default constructor and so this ends in an NoSuchMethodError.

    assertNotNull(dto);
    assertEquals(bachelorEducationDto, dto);
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

    EducationDto[] educationDtos = response.getBody().as(EducationDto[].class);
    assertEquals(3, educationDtos.length);

    // assert that all fields have a value
    Stream.of(educationDtos).forEach(educationDto -> {
      assertTrue(educationDto.getId() != 0);
      assertNotNull(educationDto.getFacility());
      assertNotNull(educationDto.getGraduation());
      assertNotNull(educationDto.getBegin());
      assertNotNull(educationDto.getEnd());
      assertNotNull(educationDto.getUserId());
      assertNotNull(educationDto.getCreatedDate());
      assertNotNull(educationDto.getCreatedBy());
      assertNotNull(educationDto.getLastModifiedDate());
      assertNotNull(educationDto.getLastModifiedBy());
    });

    // the ordering is from newest to oldest date of end
    LocalDate[] expected = new LocalDate[educationDtos.length];
    LocalDate[] actual   = new LocalDate[educationDtos.length];
    for (int index = 0; index < educationDtos.length; index++) {
      actual[index] = educationDtos[index].getEnd();
      expected[index] = actual[index];
    }

    Arrays.sort(expected, Comparator.reverseOrder());
    assertArrayEquals(expected, actual);
  }

  @Test
  void create_with_valid_request_should_return_201() {
    Map<String, String> request = educationRequestDto.toMap();

    EducationDto createdEducation =
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
              .as(EducationDto.class);

    assertNotNull(createdEducation);
    assertEquals(educationRequestDto.getUserId(), createdEducation.getUserId());
    assertEquals(educationRequestDto.getFacility(), createdEducation.getFacility());
    assertEquals(educationRequestDto.getGraduation(), createdEducation.getGraduation());
    assertEquals(educationRequestDto.getBegin(), createdEducation.getBegin());
    assertEquals(educationRequestDto.getEnd(), createdEducation.getEnd());
    assertTrue(createdEducation.getId() != 0);
    assertEquals("Administrator", createdEducation.getCreatedBy());
    assertEquals(LocalDate.now().toString(), createdEducation.getCreatedDate());
    assertEquals("Administrator", createdEducation.getLastModifiedBy());
    assertEquals(LocalDate.now().toString(), createdEducation.getLastModifiedDate());

    // remove created certification
    educationService.delete(createdEducation.getId());
  }

  @Test
  void create_with_invalid_request_should_return_400() {
    Map<String, String> request = new HashMap<>();
    request.put("facility", " ");
    request.put("begin", null);
    request.put("end", null);
    request.put("graduation", "");
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
    assertEquals(6, errorResponse.getMessages().size());
  }

  @Test
  void update_an_existing_resource_should_return_200() {
    // create a new education which can be updated
    EducationRequestDto testEducationRequest = EducationRequestDto.builder()
            .facility("Update education name")
            .graduation("grade")
            .begin(LocalDate.now())
            .end(LocalDate.now())
            .userId(UserTestUtil.USER_ID.toString())
            .build();

    EducationDto testEducation = educationService.create(testEducationRequest);

    EducationDto updatedEducationDto =
            given()
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .accept(MediaType.APPLICATION_JSON_VALUE)
              .body(educationRequestDto.toMap())
            .when()
              .pathParam("id", testEducation.getId())
              .put(URI + "/{id}")
            .then()
              .statusCode(HttpStatus.OK.value())
              .contentType(MediaType.APPLICATION_JSON_VALUE)
              .extract()
              .as(EducationDto.class);

    assertNotNull(updatedEducationDto);
    assertEquals(educationRequestDto.getFacility(), updatedEducationDto.getFacility());
    assertEquals(educationRequestDto.getGraduation(), updatedEducationDto.getGraduation());
    assertEquals(educationRequestDto.getBegin(), updatedEducationDto.getBegin());
    assertEquals(educationRequestDto.getEnd(), updatedEducationDto.getEnd());

    // id and user id should not be updated
    assertEquals(testEducation.getId(), updatedEducationDto.getId());
    assertEquals(testEducation.getUserId(), updatedEducationDto.getUserId());

    // remove created testCertification
    educationService.delete(testEducation.getId());
  }

  @Test
  void update_a_not_existing_resource_should_return_404() {
    Map<String, String> request = educationRequestDto.toMap();

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
    request.put("facility", " ");
    request.put("begin", null);
    request.put("end", null);
    request.put("graduation", "");
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
    assertEquals(6, errorResponse.getMessages().size());
  }

  @Test
  void delete_an_existing_resource_should_return_200() {
    // create a new education which can be deleted
    EducationDto testEducation = educationService.create(educationRequestDto);

    given()
    .when()
      .pathParam("id", testEducation.getId())
      .delete(URI + "/{id}")
    .then()
      .statusCode(HttpStatus.OK.value());

    assertFalse(educationService.find(testEducation.getId()).isPresent());
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
