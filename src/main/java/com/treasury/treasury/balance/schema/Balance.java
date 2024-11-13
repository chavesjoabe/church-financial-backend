package com.treasury.treasury.balance.schema;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.treasury.treasury.balance.constants.BalanceDescriptions;
import com.treasury.treasury.balance.constants.BalanceIncomingTypes;
import com.treasury.treasury.balance.constants.BalanceStatus;
import com.treasury.treasury.balance.constants.BalanceTypes;

@Document(collection = "balance")
public class Balance {

  @Id
  private String id;
  private BalanceTypes type;
  private Float value;
  private String responsible;
  private Instant createdAt;
  private Instant updatedAt;
  private BalanceStatus status;
  private Instant balanceDate;
  private BalanceDescriptions description;
  private String freeDescription;
  private String approvedBy;
  private Instant approvedAt;
  private BalanceIncomingTypes incomingType;
  private String rejectedBy;
  private Instant rejectedAt;

  public Balance(
      BalanceTypes type,
      Float value,
      String responsible,
      Instant balanceDate,
      BalanceDescriptions description,
      String freeDescription,
      BalanceIncomingTypes incomingType) {
    this.id = UUID.randomUUID().toString();
    this.type = type;
    this.value = value;
    this.responsible = responsible;
    this.createdAt = Instant.now();
    this.updatedAt = Instant.now();
    this.status = BalanceStatus.PENDING;
    this.balanceDate = balanceDate;
    this.description = description;
    this.freeDescription = freeDescription;
    this.incomingType = incomingType;
  }

  public String getId() {
    return id;
  }

  public BalanceTypes getType() {
    return type;
  }

  public void setType(BalanceTypes type) {
    this.updatedAt = Instant.now();
    this.type = type;
  }

  public Float getValue() {
    return value;
  }

  public void setValue(Float value) {
    this.updatedAt = Instant.now();
    this.value = value;
  }

  public String getResponsible() {
    return responsible;
  }

  public void setResponsible(String responsible) {
    this.updatedAt = Instant.now();
    this.responsible = responsible;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public BalanceStatus getStatus() {
    return status;
  }

  public void setStatus(BalanceStatus status) {
    this.updatedAt = Instant.now();
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

  public String getApprovedBy() {
    return approvedBy;
  }

  public void setApprovedBy(String approvedBy) {
    this.approvedBy = approvedBy;
  }

  public Instant getApprovedAt() {
    return approvedAt;
  }

  public void setApprovedAt(Instant approvedAt) {
    this.approvedAt = approvedAt;
  }

  public BalanceIncomingTypes getIncomingType() {
    return incomingType;
  }

  public void setIncomingType(BalanceIncomingTypes incomingType) {
    this.incomingType = incomingType;
  }

  public String getRejectedBy() {
    return rejectedBy;
  }

  public void setRejectedBy(String rejectedBy) {
    this.rejectedBy = rejectedBy;
  }

  public Instant getRejectedAt() {
    return rejectedAt;
  }

  public void setRejectedAt(Instant rejectedAt) {
    this.rejectedAt = rejectedAt;
  }

}
