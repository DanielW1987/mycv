package com.wagner.mycv.web.controller;

import com.wagner.mycv.service.WorkingExperienceService;
import com.wagner.mycv.testutil.WorkingExperienceTestUtil;
import com.wagner.mycv.testutil.RestAssuredRequestHandler;
import com.wagner.mycv.web.dto.ErrorResponse;
import com.wagner.mycv.web.dto.WorkingExperienceDto;
import com.wagner.mycv.web.dto.request.WorkingExperienceRequestDto;
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
class WorkingExperiencesRestControllerIntegrationTest {

  private static final String RESOURCE_PATH              = "/rest/v1/working-experiences";
  private static final String RESOURCE_ID                = "1";
  private static final String NOT_EXISTING_RESOURCE_ID   = "99999";

  @Value("${server.address}")
  private String serverAddress;

  @LocalServerPort
  private int port;

  private WorkingExperienceDto examedyDto;
  private WorkingExperienceRequestDto workingExperienceRequestDto;

  @Autowired
  private WorkingExperienceService workingExperienceService;

  private RestAssuredRequestHandler requestHandler;

  @BeforeEach
  void setUp() {
    String requestUri           = "http://" + serverAddress + ":" + port + RESOURCE_PATH;
    requestHandler              = new RestAssuredRequestHandler(requestUri);
    examedyDto                  = WorkingExperienceTestUtil.createFreelancerDto();
    workingExperienceRequestDto = WorkingExperienceTestUtil.createJavaDeveloperRequestDto();
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
    WorkingExperienceDto responseDto = response.extract().as(WorkingExperienceDto.class);

    assertNotNull(responseDto);
    assertEquals(examedyDto.getId(), responseDto.getId());
    assertEquals(examedyDto.getCompany(), responseDto.getCompany());
    assertEquals(examedyDto.getJobTitle(), responseDto.getJobTitle());
    assertEquals(examedyDto.getPlaceOfWork(), responseDto.getPlaceOfWork());
    assertEquals(examedyDto.getBegin(), responseDto.getBegin());
    assertEquals(examedyDto.getEnd(), responseDto.getEnd());
    assertEquals(examedyDto.getFocalPoints(), responseDto.getFocalPoints());
    assertEquals(examedyDto.getUserId(), responseDto.getUserId());
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

    WorkingExperienceDto[] workingExperienceDtos = response.getBody().as(WorkingExperienceDto[].class);
    assertEquals(4, workingExperienceDtos.length);

    // assert that all fields have a value
    Stream.of(workingExperienceDtos).forEach(workingExperienceDto -> {
      assertTrue(workingExperienceDto.getId() != 0);
      assertNotNull(workingExperienceDto.getCompany());
      assertNotNull(workingExperienceDto.getJobTitle());
      assertNotNull(workingExperienceDto.getPlaceOfWork());
      assertNotNull(workingExperienceDto.getBegin());
      // end may be null
      assertNotNull(workingExperienceDto.getFocalPoints());
      assertFalse(workingExperienceDto.getFocalPoints().isEmpty());
      assertNotNull(workingExperienceDto.getUserId());
      assertNotNull(workingExperienceDto.getCreatedDate());
      assertNotNull(workingExperienceDto.getCreatedBy());
      assertNotNull(workingExperienceDto.getLastModifiedDate());
      assertNotNull(workingExperienceDto.getLastModifiedBy());
    });

    // the ordering is from newest to oldest date of 'end'
    List<LocalDate> actual   = Stream.of(workingExperienceDtos).map(WorkingExperienceDto::getEnd).collect(Collectors.toList());
    List<LocalDate> expected = new ArrayList<>(actual);
    expected.sort(Comparator.nullsFirst(Comparator.reverseOrder()));

    assertEquals(expected, actual);
  }

  @Test
  void create_with_valid_request_should_return_201() {
    Map<String, Object>  request                  = workingExperienceRequestDto.toMap();
    ValidatableResponse  validatableResponse      = requestHandler.doPost(ContentType.JSON, request);
    WorkingExperienceDto createdWorkingExperience = validatableResponse.extract().as(WorkingExperienceDto.class);

    // assert
    validatableResponse.statusCode(HttpStatus.CREATED.value())
                       .contentType(ContentType.JSON);

    assertNotNull(createdWorkingExperience);
    assertNotNull(createdWorkingExperience.getUserId());
    assertEquals(workingExperienceRequestDto.getCompany(), createdWorkingExperience.getCompany());
    assertEquals(workingExperienceRequestDto.getJobTitle(), createdWorkingExperience.getJobTitle());
    assertEquals(workingExperienceRequestDto.getPlaceOfWork(), createdWorkingExperience.getPlaceOfWork());
    assertEquals(workingExperienceRequestDto.getBegin(), createdWorkingExperience.getBegin());
    assertEquals(workingExperienceRequestDto.getEnd(), createdWorkingExperience.getEnd());
    assertEquals(workingExperienceRequestDto.getFocalPoints(), createdWorkingExperience.getFocalPoints());
    assertTrue(createdWorkingExperience.getId() != 0);
    assertEquals("Administrator", createdWorkingExperience.getCreatedBy());
    assertEquals(LocalDate.now(), createdWorkingExperience.getCreatedDate());
    assertEquals("Administrator", createdWorkingExperience.getLastModifiedBy());
    assertEquals(LocalDate.now(), createdWorkingExperience.getLastModifiedDate());

    // remove created project
    workingExperienceService.delete(createdWorkingExperience.getId());
  }

  @Test
  void create_with_invalid_request_should_return_400() {
    Map<String, String> request = new HashMap<>();
    request.put("company", " ");
    request.put("begin", null);
    // request.put("end", "can not parse");
    request.put("jobTitle", "");
    request.put("placeOfWork", "valid place");

    ValidatableResponse validatableResponse = requestHandler.doPost(ContentType.JSON, request);
    ErrorResponse errorResponse             = validatableResponse.extract().as(ErrorResponse.class);

    // assert
    validatableResponse.statusCode(HttpStatus.BAD_REQUEST.value())
                       .contentType(ContentType.JSON);

    assertNotNull(errorResponse);
    assertEquals(4, errorResponse.getMessages().size());
  }

  @Test
  void update_an_existing_resource_should_return_200() {
    // create a new working experience which can be updated
    WorkingExperienceDto testWorkingExperience = workingExperienceService.create(WorkingExperienceTestUtil.createDummyRequestDto());

    Map<String, ?>        request                    = workingExperienceRequestDto.toMap();
    String                resourceId                 = Long.toString(testWorkingExperience.getId());
    ValidatableResponse   validatableResponse        = requestHandler.doPut(ContentType.JSON, request, resourceId);
    WorkingExperienceDto updatedWorkingExperienceDto = validatableResponse.extract().as(WorkingExperienceDto.class);

    // assert
    validatableResponse.statusCode(HttpStatus.OK.value())
                       .contentType(ContentType.JSON);

    assertNotNull(updatedWorkingExperienceDto);
    assertEquals(workingExperienceRequestDto.getCompany(), updatedWorkingExperienceDto.getCompany());
    assertEquals(workingExperienceRequestDto.getJobTitle(), updatedWorkingExperienceDto.getJobTitle());
    assertEquals(workingExperienceRequestDto.getPlaceOfWork(), updatedWorkingExperienceDto.getPlaceOfWork());
    assertEquals(workingExperienceRequestDto.getBegin(), updatedWorkingExperienceDto.getBegin());
    assertEquals(workingExperienceRequestDto.getEnd(), updatedWorkingExperienceDto.getEnd());
    assertEquals(workingExperienceRequestDto.getFocalPoints(), updatedWorkingExperienceDto.getFocalPoints());

    // id and user id should not be updated
    assertEquals(testWorkingExperience.getId(), updatedWorkingExperienceDto.getId());
    assertEquals(testWorkingExperience.getUserId(), updatedWorkingExperienceDto.getUserId());

    // remove created working experience
    workingExperienceService.delete(testWorkingExperience.getId());
  }

  @Test
  void update_a_not_existing_resource_should_return_404() {
    ValidatableResponse validatableResponse = requestHandler.doPut(ContentType.JSON, workingExperienceRequestDto.toMap(), NOT_EXISTING_RESOURCE_ID);

    // assert
    validatableResponse.statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  void update_with_invalid_request_should_return_400() {
    Map<String, Object> request = new HashMap<>();
    request.put("company", " ");
    request.put("begin", null);
    // request.put("end", "can not parse");
    request.put("jobTitle", "");
    request.put("placeOfWork", "valid place");

    ValidatableResponse validatableResponse = requestHandler.doPut(ContentType.JSON, request, RESOURCE_ID);
    ErrorResponse       errorResponse       = validatableResponse.extract().as(ErrorResponse.class);

    // assert
    validatableResponse.statusCode(HttpStatus.BAD_REQUEST.value())
                       .contentType(ContentType.JSON);

    assertNotNull(errorResponse);
    assertEquals(4, errorResponse.getMessages().size());
  }

  @Test
  void delete_an_existing_resource_should_return_200() {
    // create a new working experience which can be deleted
    WorkingExperienceDto testWorkingExperience = workingExperienceService.create(WorkingExperienceTestUtil.createDummyRequestDto());

    ValidatableResponse validatableResponse = requestHandler.doDelete(Long.toString(testWorkingExperience.getId()));

    // assert
    validatableResponse.statusCode(HttpStatus.OK.value());
    assertFalse(workingExperienceService.find(testWorkingExperience.getId()).isPresent());
  }

  @Test
  void delete_on_not_existing_resource_should_return_404() {
    ValidatableResponse validatableResponse = requestHandler.doDelete(NOT_EXISTING_RESOURCE_ID);

    // assert
    validatableResponse.statusCode(HttpStatus.NOT_FOUND.value());
  }
}
