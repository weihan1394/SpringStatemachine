package com.weihan1394.SpringStatemachine.Configuration;

public enum OrderEvents {
  APPROVE("Approve order"),
  DENY("Deny order"),
  CANCEL("Calcel order"),
  PROCESS("Process order");

  private String description;

  OrderEvents(String description) {
    this.description = description;
  }

  public String getDescription() {
    return this.description;
  }
}