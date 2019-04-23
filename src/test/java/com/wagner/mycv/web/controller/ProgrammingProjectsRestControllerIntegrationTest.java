package com.wagner.mycv.web.controller;

import com.wagner.mycv.service.ProgrammingProjectService;
import com.wagner.mycv.testutil.ProgrammingProjectTestUtil;
import com.wagner.mycv.utils.RestAssuredRequestHandler;
import com.wagner.mycv.web.dto.ErrorResponse;
import com.wagner.mycv.web.dto.ProgrammingProjectDto;
import com.wagner.mycv.web.dto.request.ProgrammingProjectRequestDto;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class ProgrammingProjectsRestControllerIntegrationTest {

  private static final String RESOURCE_PATH              = "/rest/v1/programming-projects";
  private static final String RESOURCE_ID                = "1";
  private static final String NOT_EXISTING_RESOURCE_ID   = "99999";

  @Value("${server.address}")
  private String serverAddress;

  @LocalServerPort
  private int port;

  private ProgrammingProjectDto examedyDto;
  private ProgrammingProjectRequestDto programmingProjectRequestDto;

  @Autowired
  private ProgrammingProjectService programmingProjectService;

  private RestAssuredRequestHandler requestHandler;

  @BeforeEach
  void setUp() {
    String requestUri            = "http://" + serverAddress + ":" + port + RESOURCE_PATH;
    requestHandler               = new RestAssuredRequestHandler(requestUri);
    examedyDto                   = ProgrammingProjectTestUtil.creteMyBeautifulCvProjectDto();
    programmingProjectRequestDto = ProgrammingProjectTestUtil.createExamedyProjectRequestDto();
  }

  @Test
  void test_get() {
    ValidatableResponse response = requestHandler.doGet(ContentType.XML, RESOURCE_ID);

    // assert
    response.contentType(ContentType.XML)
            .statusCode(HttpStatus.OK.value());

    ProgrammingProjectDto responseDto = response.extract().as(ProgrammingProjectDto.class);

    assertNotNull(responseDto);
    assertEquals(examedyDto.getId(), responseDto.getId());
    assertEquals(examedyDto.getName(), responseDto.getName());
    assertEquals(examedyDto.getDescription(), responseDto.getDescription());
    assertEquals(examedyDto.getTechnologiesUsed(), responseDto.getTechnologiesUsed());
    assertEquals(examedyDto.getVcsUrl(), responseDto.getVcsUrl());
    assertEquals(examedyDto.getUserId(), responseDto.getUserId());
  }

  @Test
  void get_on_not_existing_resource_should_return_404() {
    ValidatableResponse validatableResponse = requestHandler.doGet(ContentType.XML, NOT_EXISTING_RESOURCE_ID);

    // assert
    validatableResponse.statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  void getAll() {
    ValidatableResponse validatableResponse = requestHandler.doGetAll(ContentType.XML);

    // assert
    validatableResponse.contentType(ContentType.XML)
                       .statusCode(HttpStatus.OK.value());

    Response response = validatableResponse.extract().response();

    assertNotNull(response);
    assertNotNull(response.getBody());

    ProgrammingProjectDto[] programmingProjectDtos = response.getBody().as(ProgrammingProjectDto[].class);
    assertEquals(2, programmingProjectDtos.length);

    // assert that all fields have a value
    Stream.of(programmingProjectDtos).forEach(languageDto -> {
      assertTrue(languageDto.getId() != 0);
      assertNotNull(languageDto.getName());
      assertNotNull(languageDto.getDescription());
      assertNotNull(languageDto.getTechnologiesUsed());
      assertEquals(2, languageDto.getTechnologiesUsed().size());
      assertNotNull(languageDto.getVcsUrl());
      assertNotNull(languageDto.getUserId());
      assertNotNull(languageDto.getCreatedDate());
      assertNotNull(languageDto.getCreatedBy());
      assertNotNull(languageDto.getLastModifiedDate());
      assertNotNull(languageDto.getLastModifiedBy());
    });
  }

  @Test
  void create_with_valid_request_should_return_201() {
    Map<String, Object>   request             = programmingProjectRequestDto.toMap();
    ValidatableResponse   validatableResponse = requestHandler.doPost(ContentType.XML, request);
    ProgrammingProjectDto createdProject      = validatableResponse.extract().as(ProgrammingProjectDto.class);

    // assert
    validatableResponse.statusCode(HttpStatus.CREATED.value())
                       .contentType(ContentType.XML);

    assertNotNull(createdProject);
    assertNotNull(createdProject.getUserId());
    assertEquals(programmingProjectRequestDto.getName(), createdProject.getName());
    assertEquals(programmingProjectRequestDto.getDescription(), createdProject.getDescription());
    assertEquals(programmingProjectRequestDto.getTechnologiesUsed(), createdProject.getTechnologiesUsed());
    assertEquals(programmingProjectRequestDto.getVcsUrl(), createdProject.getVcsUrl());
    assertTrue(createdProject.getId() != 0);
    assertEquals("Administrator", createdProject.getCreatedBy());
    assertEquals(LocalDate.now().toString(), createdProject.getCreatedDate());
    assertEquals("Administrator", createdProject.getLastModifiedBy());
    assertEquals(LocalDate.now().toString(), createdProject.getLastModifiedDate());

    // remove created project
    programmingProjectService.delete(createdProject.getId());
  }

  @Test
  void create_with_invalid_request_should_return_400() {
    Map<String, String> request = new HashMap<>();
    request.put("name", "  ");

    ValidatableResponse validatableResponse = requestHandler.doPost(ContentType.JSON, request);
    ErrorResponse errorResponse             = validatableResponse.extract().as(ErrorResponse.class);

    // assert
    validatableResponse.statusCode(HttpStatus.BAD_REQUEST.value())
                       .contentType(ContentType.JSON);

    assertNotNull(errorResponse);
    assertEquals(3, errorResponse.getMessages().size());
  }

  @Test
  void update_an_existing_resource_should_return_200() {
    // create a new project which can be updated
    ProgrammingProjectRequestDto testProjectRequest = ProgrammingProjectRequestDto.builder()
            .name("update language")
            .description("update descruption")
            .technologiesUsed(Arrays.asList("C#", "C++"))
            .vcsUrl("update url")
            .build();

    ProgrammingProjectDto testProject = programmingProjectService.create(testProjectRequest);

    Map<String, ?>        request                      = programmingProjectRequestDto.toMap();
    String                resourceId                   = Long.toString(testProject.getId());
    ValidatableResponse   validatableResponse          = requestHandler.doPut(ContentType.XML, request, resourceId);
    ProgrammingProjectDto updatedProgrammingProjectDto = validatableResponse.extract().as(ProgrammingProjectDto.class);

    // assert
    validatableResponse.statusCode(HttpStatus.OK.value())
                       .contentType(ContentType.XML);

    assertNotNull(updatedProgrammingProjectDto);
    assertEquals(programmingProjectRequestDto.getName(), updatedProgrammingProjectDto.getName());
    assertEquals(programmingProjectRequestDto.getDescription(), updatedProgrammingProjectDto.getDescription());
    assertEquals(programmingProjectRequestDto.getTechnologiesUsed(), updatedProgrammingProjectDto.getTechnologiesUsed());
    assertEquals(programmingProjectRequestDto.getVcsUrl(), updatedProgrammingProjectDto.getVcsUrl());

    // id and user id should not be updated
    assertEquals(testProject.getId(), updatedProgrammingProjectDto.getId());
    assertEquals(testProject.getUserId(), updatedProgrammingProjectDto.getUserId());

    // remove created project
    programmingProjectService.delete(testProject.getId());
  }

  @Test
  void update_a_not_existing_resource_should_return_404() {
    ValidatableResponse validatableResponse = requestHandler.doPut(ContentType.JSON, programmingProjectRequestDto.toMap(), NOT_EXISTING_RESOURCE_ID);

    // assert
    validatableResponse.statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  void update_with_invalid_request_should_return_400() {
    Map<String, Object> request = new HashMap<>();
    request.put("name", null);
    request.put("technologiesUsed", Collections.emptyList());

    ValidatableResponse validatableResponse = requestHandler.doPut(ContentType.JSON, request, RESOURCE_ID);
    ErrorResponse       errorResponse       = validatableResponse.extract().as(ErrorResponse.class);

    // assert
    validatableResponse.statusCode(HttpStatus.BAD_REQUEST.value())
                       .contentType(ContentType.JSON);

    assertNotNull(errorResponse);
    assertEquals(2, errorResponse.getMessages().size());
  }

  @Test
  void delete_an_existing_resource_should_return_200() {
    // create a new language which can be deleted
    ProgrammingProjectDto testProject = programmingProjectService.create(programmingProjectRequestDto);

    ValidatableResponse validatableResponse = requestHandler.doDelete(Long.toString(testProject.getId()));

    // assert
    validatableResponse.statusCode(HttpStatus.OK.value());
    assertFalse(programmingProjectService.find(testProject.getId()).isPresent());
  }

  @Test
  void delete_on_not_existing_resource_should_return_404() {
    ValidatableResponse validatableResponse = requestHandler.doDelete(NOT_EXISTING_RESOURCE_ID);

    // assert
    validatableResponse.statusCode(HttpStatus.NOT_FOUND.value());
  }
}
