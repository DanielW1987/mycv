package com.wagner.mycv.web.controller;

import com.wagner.mycv.service.TechnologySkillService;
import com.wagner.mycv.testutil.TechnologySkillTestUtil;
import com.wagner.mycv.utils.RestAssuredRequestHandler;
import com.wagner.mycv.web.dto.ErrorResponse;
import com.wagner.mycv.web.dto.TechnologySkillDto;
import com.wagner.mycv.web.dto.request.TechnologySkillRequestDto;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class TechnologySkillsRestControllerIntegrationTest {

  private static final String RESOURCE_PATH              = "/rest/v1/technology-skills";
  private static final String RESOURCE_ID                = "1";
  private static final String NOT_EXISTING_RESOURCE_ID   = "99999";

  @Value("${server.address}")
  private String serverAddress;

  @LocalServerPort
  private int port;

  private TechnologySkillDto examedyDto;
  private TechnologySkillRequestDto technologySkillRequestDto;

  @Autowired
  private TechnologySkillService technologySkillService;

  private RestAssuredRequestHandler requestHandler;

  @BeforeEach
  void setUp() {
    String requestUri         = "http://" + serverAddress + ":" + port + RESOURCE_PATH;
    requestHandler            = new RestAssuredRequestHandler(requestUri);
    examedyDto                = TechnologySkillTestUtil.createProgrammingTechnologySkillDto();
    technologySkillRequestDto = TechnologySkillTestUtil.createProgrammingSkillRequestDto();
  }

  @Test
  void test_get() {
    ValidatableResponse response = requestHandler.doGet(ContentType.JSON, RESOURCE_ID);

    // assert
    response.contentType(ContentType.JSON)
            .statusCode(HttpStatus.OK.value());

    TechnologySkillDto responseDto = response.extract().as(TechnologySkillDto.class);

    assertNotNull(responseDto);
    assertEquals(examedyDto.getId(), responseDto.getId());
    assertEquals(examedyDto.getCategory(), responseDto.getCategory());
    assertEquals(examedyDto.getSkillNames(), responseDto.getSkillNames());
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

    TechnologySkillDto[] technologySkillDtos = response.getBody().as(TechnologySkillDto[].class);
    assertEquals(6, technologySkillDtos.length);

    // assert that all fields have a value
    Stream.of(technologySkillDtos).forEach(technologySkillDto -> {
      assertTrue(technologySkillDto.getId() != 0);
      assertNotNull(technologySkillDto.getCategory());
      assertNotNull(technologySkillDto.getSkillNames());
      assertNotNull(technologySkillDto.getUserId());
      assertNotNull(technologySkillDto.getCreatedDate());
      assertNotNull(technologySkillDto.getCreatedBy());
      assertNotNull(technologySkillDto.getLastModifiedDate());
      assertNotNull(technologySkillDto.getLastModifiedBy());
    });
  }

  @Test
  void create_with_valid_request_should_return_201() {
    Map<String, Object> request             = technologySkillRequestDto.toMap();
    ValidatableResponse validatableResponse = requestHandler.doPost(ContentType.JSON, request);
    TechnologySkillDto  createdSkill        = validatableResponse.extract().as(TechnologySkillDto.class);

    // assert
    validatableResponse.statusCode(HttpStatus.CREATED.value())
                       .contentType(ContentType.JSON);

    assertNotNull(createdSkill);
    assertNotNull(createdSkill.getUserId());
    assertEquals(technologySkillRequestDto.getCategory(), createdSkill.getCategory());
    assertEquals(technologySkillRequestDto.getSkillNames(), createdSkill.getSkillNames());
    assertTrue(createdSkill.getId() != 0);
    assertEquals("Administrator", createdSkill.getCreatedBy());
    assertEquals(LocalDate.now().toString(), createdSkill.getCreatedDate());
    assertEquals("Administrator", createdSkill.getLastModifiedBy());
    assertEquals(LocalDate.now().toString(), createdSkill.getLastModifiedDate());

    // remove created skill
    technologySkillService.delete(createdSkill.getId());
  }

  @Test
  void create_with_invalid_request_should_return_400() {
    Map<String, String> request = new HashMap<>();
    request.put("category", "");
    request.put("skillNames", null);

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
    // create a new technology skill which can be updated
    TechnologySkillRequestDto testSkillRequest = TechnologySkillRequestDto.builder()
            .category("update category")
            .skillNames(Collections.emptyList())
            .build();

    TechnologySkillDto testSkill = technologySkillService.create(testSkillRequest);

    Map<String, ?>      request                   = technologySkillRequestDto.toMap();
    String              resourceId                = Long.toString(testSkill.getId());
    ValidatableResponse validatableResponse       = requestHandler.doPut(ContentType.JSON, request, resourceId);
    TechnologySkillDto  updatedTechnologySkillDto = validatableResponse.extract().as(TechnologySkillDto.class);

    // assert
    validatableResponse.statusCode(HttpStatus.OK.value())
                       .contentType(ContentType.JSON);

    assertNotNull(updatedTechnologySkillDto);
    assertEquals(technologySkillRequestDto.getCategory(), updatedTechnologySkillDto.getCategory());
    assertEquals(technologySkillRequestDto.getSkillNames(), updatedTechnologySkillDto.getSkillNames());

    // id and user id should not be updated
    assertEquals(testSkill.getId(), updatedTechnologySkillDto.getId());
    assertEquals(testSkill.getUserId(), updatedTechnologySkillDto.getUserId());

    // remove created technology skill
    technologySkillService.delete(testSkill.getId());
  }

  @Test
  void update_a_not_existing_resource_should_return_404() {
    ValidatableResponse validatableResponse = requestHandler.doPut(ContentType.JSON, technologySkillRequestDto.toMap(), NOT_EXISTING_RESOURCE_ID);

    // assert
    validatableResponse.statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  void update_with_invalid_request_should_return_400() {
    Map<String, Object> request = new HashMap<>();
    request.put("category", "valid category name");
    request.put("skillNames", null);

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
    // create a new technology skill which can be deleted
    TechnologySkillDto testSkill = technologySkillService.create(technologySkillRequestDto);

    ValidatableResponse validatableResponse = requestHandler.doDelete(Long.toString(testSkill.getId()));

    // assert
    validatableResponse.statusCode(HttpStatus.OK.value());
    assertFalse(technologySkillService.find(testSkill.getId()).isPresent());
  }

  @Test
  void delete_on_not_existing_resource_should_return_404() {
    ValidatableResponse validatableResponse = requestHandler.doDelete(NOT_EXISTING_RESOURCE_ID);

    // assert
    validatableResponse.statusCode(HttpStatus.NOT_FOUND.value());
  }
}
