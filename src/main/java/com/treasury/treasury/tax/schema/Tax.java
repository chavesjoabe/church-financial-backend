package com.treasury.treasury.tax.schema;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tax")
public class Tax {
  @Id
  private String id;
  private Double leaderPercentage;
  private Double mainChurchPercentage;
  private Double ministryPercentage;
  private Double mainLeaderPercentage;
  private Instant createdAt;
  private Instant updatedAt;

  public Tax() {
  }

  public Tax(
      Double leaderPercentage,
      Double mainChurchPercentage,
      Double ministryPercentage,
      Double mainLeaderPercentage) {
    this.id = UUID.randomUUID().toString();
    this.leaderPercentage = leaderPercentage;
    this.mainChurchPercentage = mainChurchPercentage;
    this.ministryPercentage = ministryPercentage;
    this.mainLeaderPercentage = mainLeaderPercentage;
    this.createdAt = Instant.now();
    this.updatedAt = Instant.now();
  }

  public String getId() {
    return id;
  }

  public Double getLeaderPercentage() {
    return leaderPercentage;
  }

  public void setLeaderPercentage(Double leaderPercentage) {
    this.leaderPercentage = leaderPercentage;
  }

  public Double getMainChurchPercentage() {
    return mainChurchPercentage;
  }

  public void setMainChurchPercentage(Double mainChurchPercentage) {
    this.mainChurchPercentage = mainChurchPercentage;
  }

  public Double getMinistryPercentage() {
    return ministryPercentage;
  }

  public void setMinistryPercentage(Double ministryPercentage) {
    this.ministryPercentage = ministryPercentage;
  }

  public Double getMainLeaderPercentage() {
    return mainLeaderPercentage;
  }

  public void setMainLeaderPercentage(Double mainLeaderPercentage) {
    this.mainLeaderPercentage = mainLeaderPercentage;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }
}
