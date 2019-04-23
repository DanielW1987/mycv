package com.wagner.mycv.web.controller;

import com.wagner.mycv.service.CertificationService;
import com.wagner.mycv.testutil.CertificationTestUtil;
import com.wagner.mycv.utils.RestAssuredRequestHandler;
import com.wagner.mycv.web.dto.CertificationDto;
import com.wagner.mycv.web.dto.EducationDto;
import com.wagner.mycv.web.dto.ErrorResponse;
import com.wagner.mycv.web.dto.request.CertificationRequestDto;
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
import static org.springframework.boot.test.context.SpringBootTest.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
class CertificationsRestControllerIntegrationTest {

  private static final String RESOURCE_PATH              = "/rest/v1/certifications";
  private static final String RESOURCE_ID                = "1";
  private static final String NOT_EXISTING_RESOURCE_ID   = "99999";

  @Value("${server.address}")
  private String serverAddress;

  @LocalServerPort
  private int port;

  private CertificationDto        mtaCertificationDto;
  private CertificationRequestDto certificationRequestDto;

  @Autowired
  private CertificationService certificationService;

  private RestAssuredRequestHandler requestHandler;

  @BeforeEach
  void setUp() {
    String requestUri       = "http://" + serverAddress + ":" + port + RESOURCE_PATH;
    requestHandler          = new RestAssuredRequestHandler(requestUri);
    mtaCertificationDto     = CertificationTestUtil.createMtaCertificationDto();
    certificationRequestDto = CertificationTestUtil.createTestCertificationRequestDto();
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
    CertificationDto responseDto = response.extract().as(CertificationDto.class);

    assertNotNull(responseDto);
    assertEquals(mtaCertificationDto.getId(), responseDto.getId());
    assertEquals(mtaCertificationDto.getName(), responseDto.getName());
    assertEquals(mtaCertificationDto.getDateOfAchievement(), responseDto.getDateOfAchievement());
    assertEquals(mtaCertificationDto.getCertificate(), responseDto.getCertificate());
    assertEquals(mtaCertificationDto.getUserId(), responseDto.getUserId());
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

    CertificationDto[] certificationDtos = response.getBody().as(CertificationDto[].class);
    assertEquals(3, certificationDtos.length);

    // assert that all fields have a value
    Stream.of(certificationDtos).forEach(certificationDto -> {
      assertTrue(certificationDto.getId() != 0);
      assertNotNull(certificationDto.getName());
      assertNotNull(certificationDto.getDateOfAchievement());
      assertNotNull(certificationDto.getCertificate());
      assertNotNull(certificationDto.getUserId());
      assertNotNull(certificationDto.getCreatedDate());
      assertNotNull(certificationDto.getCreatedBy());
      assertNotNull(certificationDto.getLastModifiedDate());
      assertNotNull(certificationDto.getLastModifiedBy());
    });

    // the ordering is from newest to oldest 'dateOfAchievement'
    List<LocalDate> actual   = Stream.of(certificationDtos).map(CertificationDto::getDateOfAchievement).collect(Collectors.toList());
    List<LocalDate> expected = new ArrayList<>(actual);
    expected.sort(Comparator.reverseOrder());

    assertEquals(expected, actual);
  }

  @Test
  void create_with_valid_request_should_return_201() {
    Map<String, String> request              = certificationRequestDto.toMap();
    ValidatableResponse validatableResponse  = requestHandler.doPost(ContentType.JSON, request);
    CertificationDto    createdCertification = validatableResponse.extract().as(CertificationDto.class);

    // assert
    validatableResponse.statusCode(HttpStatus.CREATED.value())
                       .contentType(ContentType.JSON);

    assertNotNull(createdCertification);
    assertNotNull(createdCertification.getUserId());
    assertEquals(certificationRequestDto.getName(), createdCertification.getName());
    assertEquals(certificationRequestDto.getDateOfAchievement(), createdCertification.getDateOfAchievement());
    assertEquals(certificationRequestDto.getCertificate(), createdCertification.getCertificate());
    assertTrue(createdCertification.getId() != 0);
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
    // create a new certification which can be updated
    CertificationRequestDto testCertificationRequest = CertificationRequestDto.builder()
            .name("Updated certification name")
            .dateOfAchievement(LocalDate.now())
            .certificate("certification file")
            .build();

    CertificationDto testCertification = certificationService.create(testCertificationRequest);

    String idForUpdate = Long.toString(testCertification.getId());
    ValidatableResponse validatableResponse     = requestHandler.doPut(ContentType.JSON, certificationRequestDto.toMap(), idForUpdate);
    CertificationDto    updatedCertificationDto = validatableResponse.extract().as(CertificationDto.class);

    // assert
    validatableResponse.statusCode(HttpStatus.OK.value())
                       .contentType(ContentType.JSON);

    assertNotNull(updatedCertificationDto);
    assertEquals(certificationRequestDto.getDateOfAchievement(), updatedCertificationDto.getDateOfAchievement());
    assertEquals(certificationRequestDto.getName(), updatedCertificationDto.getName());
    assertEquals(certificationRequestDto.getCertificate(), updatedCertificationDto.getCertificate());

    // id and user id should not be updated
    assertEquals(testCertification.getId(), updatedCertificationDto.getId());
    assertEquals(testCertification.getUserId(), updatedCertificationDto.getUserId());

    // remove created testCertification
    certificationService.delete(testCertification.getId());
  }

  @Test
  void update_a_not_existing_resource_should_return_404() {
    Map<String, String> request = certificationRequestDto.toMap();
    ValidatableResponse validatableResponse = requestHandler.doPut(ContentType.JSON, request, NOT_EXISTING_RESOURCE_ID);

    // assert
    validatableResponse.statusCode(HttpStatus.NOT_FOUND.value());
  }

  @Test
  void update_with_invalid_request_should_return_400() {
    Map<String, String> request = new HashMap<>();
    request.put("name", "A valid name");
    request.put("dateOfAchievement", "");

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
    // create a new certification which can be deleted
    CertificationDto testCertification = certificationService.create(certificationRequestDto);

    ValidatableResponse validatableResponse = requestHandler.doDelete(Long.toString(testCertification.getId()));

    // assert
    validatableResponse.statusCode(HttpStatus.OK.value());

    assertFalse(certificationService.find(testCertification.getId()).isPresent());
  }

  @Test
  void delete_on_not_existing_resource_should_return_404() {
    ValidatableResponse validatableResponse = requestHandler.doDelete(NOT_EXISTING_RESOURCE_ID);

    // assert
    validatableResponse.statusCode(HttpStatus.NOT_FOUND.value());
  }
}