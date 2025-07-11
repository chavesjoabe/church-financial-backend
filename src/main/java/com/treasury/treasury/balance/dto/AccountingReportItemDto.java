package com.treasury.treasury.balance.dto;

import java.time.Instant;

import com.treasury.treasury.balance.constants.BalanceDescriptions;
import com.treasury.treasury.balance.constants.BalanceIncomingTypes;
import com.treasury.treasury.balance.constants.BalanceStatus;
import com.treasury.treasury.balance.constants.BalanceTypes;
import com.treasury.treasury.balance.schema.Balance;
import com.treasury.treasury.tax.schema.Tax;

public class AccountingReportItemDto {
  private String balanceId;
  private BalanceTypes type;
  private Float value;
  private String responsible;
  private BalanceStatus status;
  private Instant balanceDate;
  private BalanceDescriptions description;
  private String freeDescription;
  private BalanceIncomingTypes incomingType;
  private Float churchFirstLeaderPercentage;
  private Float churchSecondLeaderPercentage;
  private Float mainChurchPercentage;
  private Float ministryPercentage;
  private Float mainLeaderPercentage;

  public AccountingReportItemDto() {
  }

  public AccountingReportItemDto(
      String balanceId,
      BalanceTypes type,
      Float value,
      String responsible,
      BalanceStatus status,
      Instant balanceDate,
      BalanceDescriptions description,
      String freeDescription,
      BalanceIncomingTypes incomingType) {
    this.balanceId = balanceId;
    this.type = type;
    this.value = value;
    this.responsible = responsible;
    this.status = status;
    this.balanceDate = balanceDate;
    this.description = description;
    this.freeDescription = freeDescription;
    this.incomingType = incomingType;
    this.churchFirstLeaderPercentage = (float) (value * 0.125);
    this.churchSecondLeaderPercentage = (float) (value * 0.125);
    this.mainChurchPercentage = (float) (value * 0.11);
    this.ministryPercentage = (float) (value * 0.04);
    this.mainLeaderPercentage = (float) (value * 0.02);
  }

  public AccountingReportItemDto(Balance balance, Tax tax) {
    this.balanceId = balance.getId();
    this.type = balance.getType();
    this.value = balance.getValue();
    this.responsible = balance.getResponsible();
    this.status = balance.getStatus();
    this.balanceDate = balance.getBalanceDate();
    this.description = balance.getDescription();
    this.freeDescription = balance.getFreeDescription();
    this.incomingType = balance.getIncomingType();
    this.churchFirstLeaderPercentage = (float) (value * tax.getFirstLeaderPercentage());
    this.churchSecondLeaderPercentage = (float) (value * tax.getSecondLeaderPercentage());
    this.mainChurchPercentage = (float) (value * tax.getMainChurchPercentage());
    this.ministryPercentage = (float) (value * tax.getMinistryPercentage());
    this.mainLeaderPercentage = (float) (value * tax.getMainLeaderPercentage());
  }

  public String getBalanceId() {
    return balanceId;
  }

  public void setBalanceId(String balanceId) {
    this.balanceId = balanceId;
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

  public BalanceStatus getStatus() {
    return status;
  }

  public void setStatus(BalanceStatus status) {
    this.status = status;
  }

  public Instant getBalanceDate() {
    return balanceDate;
  }

  public void setBalanceDate(Instant balanceDate) {
    this.balanceDate = balanceDate;
  }

  public BalanceDescriptions getDescription() {
    return description;
  }

  public void setDescription(BalanceDescriptions description) {
    this.description = description;
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

  public void setIncomingType(BalanceIncomingTypes incomingType) {
    this.incomingType = incomingType;
  }

  public Float getChurchFirstLeaderPercentage() {
    return churchFirstLeaderPercentage;
  }

  public void setChurchFirstLeaderPercentage(Float churchFirstLeaderPercentage) {
    this.churchFirstLeaderPercentage = churchFirstLeaderPercentage;
  }

  public Float getChurchSecondLeaderPercentage() {
    return churchSecondLeaderPercentage;
  }

  public void setChurchSecondLeaderPercentage(Float churchSecondLeaderPercentage) {
    this.churchSecondLeaderPercentage = churchSecondLeaderPercentage;
  }

  public Float getMainChurchPercentage() {
    return mainChurchPercentage;
  }

  public void setMainChurchPercentage(Float mainChurchPercentage) {
    this.mainChurchPercentage = mainChurchPercentage;
  }

  public Float getMinistryPercentage() {
    return ministryPercentage;
  }

  public void setMinistryPercentage(Float ministryPercentage) {
    this.ministryPercentage = ministryPercentage;
  }

  public Float getMainLeaderPercentage() {
    return mainLeaderPercentage;
  }

  public void setMainLeaderPercentage(Float mainLeaderPercentage) {
    this.mainLeaderPercentage = mainLeaderPercentage;
  }
}
