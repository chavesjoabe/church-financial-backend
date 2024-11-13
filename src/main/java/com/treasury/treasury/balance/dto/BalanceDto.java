package com.treasury.treasury.balance.dto;

import java.time.Instant;

import com.treasury.treasury.balance.constants.BalanceDescriptions;
import com.treasury.treasury.balance.constants.BalanceIncomingTypes;
import com.treasury.treasury.balance.constants.BalanceTypes;

public class BalanceDto {
  private BalanceTypes type;
  private Float value;
  private String responsible;
  private Instant balanceDate;
  private BalanceDescriptions description;
  private String freeDescription;
  private BalanceIncomingTypes incomingType;

  public BalanceDto(
      BalanceTypes type,
      Float value,
      String responsible,
      Instant balanceDate,
      BalanceDescriptions description,
      String freeDescription,
      BalanceIncomingTypes incomingTypes) {
    this.type = type;
    this.value = value;
    this.responsible = responsible;
    this.balanceDate = balanceDate;
    this.description = description;
    this.freeDescription = freeDescription;
    this.incomingType= incomingTypes;
  }

  public BalanceDescriptions getDescription() {
    return description;
  }

  public void setDescription(BalanceDescriptions description) {
    this.description = description;
  }

  public BalanceTypes getType() {
    return type;
  }

  public void setType(BalanceTypes type) {
    this.type = type;
  }

  public Float getValue() {
    return value;
  }

  public void setValue(Float value) {
    this.value = value;
  }

  public String getResponsible() {
    return responsible;
  }

  public void setResponsible(String responsible) {
    this.responsible = responsible;
  }

  public Instant getBalanceDate() {
    return balanceDate;
  }

  public void setBalanceDate(Instant balanceDate) {
    this.balanceDate = balanceDate;
  }

  public String getFreeDescription() {
    return freeDescription;
  }

  public void setFreeDescription(String freeDescription) {
    this.freeDescription = freeDescription;
  }

  public BalanceIncomingTypes getIncomingType() {
    return incomingType;
  }

  public void setIncomingType(BalanceIncomingTypes incomingTypes) {
    this.incomingType = incomingTypes;
  }
}
