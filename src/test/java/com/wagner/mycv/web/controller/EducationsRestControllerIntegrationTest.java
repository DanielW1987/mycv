package com.wagner.mycv.web.controller;

import com.wagner.mycv.service.EducationService;
import com.wagner.mycv.testutil.EducationTestUtil;
import com.wagner.mycv.testutil.RestAssuredRequestHandler;
import com.wagner.mycv.web.dto.EducationDto;
import com.wagner.mycv.web.dto.ErrorResponse;
import com.wagner.mycv.web.dto.request.EducationRequestDto;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class EducationsRestControllerIntegrationTest {

  private static final String RESOURCE_PATH            = "/rest/v1/educations";
  private static final String RESOURCE_ID              = "1";
  private static final String NOT_EXISTING_RESOURCE_ID = "99999";

  @Value("${server.address}")
  private String serverAddress;

  @LocalServerPort
  private int port;

  private EducationDto        bachelorEducationDto;
  private EducationRequestDto educationRequestDto;

  @Autowired
  private EducationService educationService;

  private RestAssuredRequestHandler requestHandler;

  @BeforeEach
  void setUp() {
    String requestUri    = "http://" + serverAddress + ":" + port + RESOURCE_PATH;
    requestHandler       = new RestAssuredRequestHandler(requestUri);
    bachelorEducationDto = EducationTestUtil.createHighschoolEducationDto();
    educationRequestDto  = EducationTestUtil.createBachelorEducationRequestDto();
  }

  @Test
  void test_get() {
    ValidatableResponse response = requestHandler.doGet(ContentType.JSON, RESOURCE_ID);

    // assert
    response.contentType(ContentType.JSON)
            .statusCode(HttpStatus.OK.value());

    // restassured tries to unmarshal LocalDate values via the default constructor of LocalDate if the requested content type is XML.
    // LocalDate has no default constructor and so this ends in an NoSuchMethodError.
    // That's why the extract as DTO method only works if content typ of request was JSON.
    EducationDto responseDto = response.extract().as(EducationDto.class);

    assertNotNull(responseDto);
    assertEquals(bachelorEducationDto.getId(), responseDto.getId());
    assertEquals(bachelorEducationDto.getFacility(), responseDto.getFacility());
    assertEquals(bachelorEducationDto.getGraduation(), responseDto.getGraduation());
    assertEquals(bachelorEducationDto.getBegin(), responseDto.getBegin());
    assertEquals(bachelorEducationDto.getEnd(), responseDto.getEnd());
    assertEquals(bachelorEducationDto.getUserId(), responseDto.getUserId());
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

    // the ordering is from newest to oldest date of 'end'
    List<LocalDate> actual   = Stream.of(educationDtos).map(EducationDto::getEnd).collect(Collectors.toList());
    List<LocalDate> expected = new ArrayList<>(actual);
    expected.sort(Comparator.reverseOrder());

    assertEquals(expected, actual);
  }

  @Test
  void create_with_valid_request_should_return_201() {
    Map<String, String> request             = educationRequestDto.toMap();
    ValidatableResponse validatableResponse = requestHandler.doPost(ContentType.JSON, request);
    EducationDto        createdEducation    = validatableResponse.extract().as(EducationDto.class);

    // assert
    validatableResponse.statusCode(HttpStatus.CREATED.value())
                       .contentType(ContentType.JSON);

    assertNotNull(createdEducation);
    assertNotNull(createdEducation.getUserId());
    assertEquals(educationRequestDto.getFacility(), createdEducation.getFacility());
    assertEquals(educationRequestDto.getGraduation(), createdEducation.getGraduation());
    assertEquals(educationRequestDto.getBegin(), createdEducation.getBegin());
    assertEquals(educationRequestDto.getEnd(), createdEducation.getEnd());
    assertTrue(createdEducation.getId() != 0);
    assertEquals("Administrator", createdEducation.getCreatedBy());
    assertEquals(LocalDate.now(), createdEducation.getCreatedDate());
    assertEquals("Administrator", createdEducation.getLastModifiedBy());
    assertEquals(LocalDate.now(), createdEducation.getLastModifiedDate());

    // remove created education
    educationService.delete(createdEducation.getId());
  }

  @Test
  void create_with_invalid_request_should_return_400() {
    Map<String, String> request = new HashMap<>();
    request.put("facility", " ");
    request.put("begin", null);
    request.put("end", null);
    request.put("graduation", "");

    ValidatableResponse validatableResponse = requestHandler.doPost(ContentType.JSON, request);
    ErrorResponse       errorResponse       = validatableResponse.extract().as(ErrorResponse.class);

    // assert
    validatableResponse.statusCode(HttpStatus.BAD_REQUEST.value())
                       .contentType(ContentType.JSON);

    assertNotNull(errorResponse);
    assertEquals(5, errorResponse.getMessages().size());
  }

  @Test
  void update_an_existing_resource_should_return_200() {
    // create a new education which can be updated
    EducationRequestDto testEducationRequest = EducationRequestDto.builder()
            .facility("Update education name")
            .graduation("grade")
            .begin(LocalDate.now())
            .end(LocalDate.now())
            .build();

    EducationDto testEducation = educationService.create(testEducationRequest);

    ValidatableResponse validatableResponse = requestHandler.doPut(ContentType.JSON, educationRequestDto.toMap(), Long.toString(testEducation.getId()));
    EducationDto        updatedEducationDto = validatableResponse.extract().as(EducationDto.class);

    // assert
    validatableResponse.statusCode(HttpStatus.OK.value())
                       .contentType(ContentType.JSON);

    assertNotNull(updatedEducationDto);
    assertEquals(educationRequestDto.getFacility(), updatedEducationDto.getFacility());
    assertEquals(educationRequestDto.getGraduation(), updatedEducationDto.getGraduation());
    assertEquals(educationRequestDto.getBegin(), updatedEducationDto.getBegin());
    assertEquals(educationRequestDto.getEnd(), updatedEducationDto.getEnd());

    // id and user id should not be updated
    assertEquals(testEducation.getId(), updatedEducationDto.getId());
    assertEquals(testEducation.getUserId(), updatedEducationDto.getUserId());

    // remove created education
    educationService.delete(testEducation.getId());
  }

  @Test
  void update_a_not_existing_resource_should_return_404() {
    ValidatableResponse validatableResponse = requestHandler.doPut(ContentType.JSON, educationRequestDto.toMap(), NOT_EXISTING_RESOURCE_ID);

    // assert
    validatableResponse.statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  void update_with_invalid_request_should_return_400() {
    Map<String, String> request = new HashMap<>();
    request.put("facility", " ");
    request.put("begin", null);
    request.put("end", null);
    request.put("graduation", "");

    ValidatableResponse validatableResponse = requestHandler.doPut(ContentType.JSON, request, RESOURCE_ID);
    ErrorResponse       errorResponse       = validatableResponse.extract().as(ErrorResponse.class);

    // assert
    validatableResponse.statusCode(HttpStatus.BAD_REQUEST.value())
                       .contentType(ContentType.JSON);

    assertNotNull(errorResponse);
    assertEquals(5, errorResponse.getMessages().size());
  }

  @Test
  void delete_an_existing_resource_should_return_200() {
    // create a new education which can be deleted
    EducationDto testEducation = educationService.create(educationRequestDto);

    ValidatableResponse validatableResponse = requestHandler.doDelete(Long.toString(testEducation.getId()));

    // assert
    validatableResponse.statusCode(HttpStatus.OK.value());
    assertFalse(educationService.find(testEducation.getId()).isPresent());
  }

  @Test
  void delete_on_not_existing_resource_should_return_404() {
    ValidatableResponse validatableResponse = requestHandler.doDelete(NOT_EXISTING_RESOURCE_ID);

    // assert
    validatableResponse.statusCode(HttpStatus.NOT_FOUND.value());
  }
}
