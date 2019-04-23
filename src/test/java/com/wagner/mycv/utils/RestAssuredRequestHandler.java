package com.wagner.mycv.utils;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestAssuredRequestHandler {

  private final String requestUri;

  public RestAssuredRequestHandler(String requestUri) {
    this.requestUri = requestUri;
  }

  public ValidatableResponse doGet(ContentType accept, String resourceId) {
    return given()
            .accept(accept)
            .pathParam("id", resourceId)
           .when()
            .get(requestUri + "/{id}")
           .then();
  }

  public ValidatableResponse doGetAll(ContentType accept) {
    return given()
            .accept(accept)
           .when()
            .get(requestUri)
           .then();
  }

  public ValidatableResponse doPost(ContentType accept, Map<String, ?> request) {
    return given()
            .contentType(accept)
            .accept(accept)
            .body(request)
           .when()
            .post(requestUri)
           .then();
  }

  public ValidatableResponse doPut(ContentType accept, Map<String, ?> request, String resourceId) {
    return given()
            .contentType(accept)
            .accept(accept)
            .body(request)
           .when()
            .pathParam("id", resourceId)
            .put(requestUri + "/{id}")
           .then();
  }

  public ValidatableResponse doDelete(String resourceId) {
    return given()
           .when()
            .pathParam("id", resourceId)
            .delete(requestUri + "/{id}")
           .then();
  }

}
