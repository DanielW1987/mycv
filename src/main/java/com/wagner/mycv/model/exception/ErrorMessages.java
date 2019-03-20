package com.wagner.mycv.model.exception;

public enum ErrorMessages {

  VALIDATION_ERROR("There were errors validating your request. Please refer to the documentation for a valid request.");

  private final String errorMessage;
	
  ErrorMessages(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String toDisplayString() {
    return errorMessage;
  }

}