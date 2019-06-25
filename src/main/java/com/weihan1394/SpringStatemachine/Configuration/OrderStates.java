package com.weihan1394.SpringStatemachine.Configuration;

public enum OrderStates {
  CREATED("New order"),
  APPROVED("Approved"),
  CANCELLED("Cancelled"),
  DENIED("Denied"),
  PROCESSED("Processed");

  private String description;

  OrderStates(String description) {
    this.description = description;
  }

  public String getDescription() {
    return this.description;
  }
}
