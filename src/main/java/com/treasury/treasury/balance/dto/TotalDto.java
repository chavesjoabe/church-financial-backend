package com.treasury.treasury.balance.dto;

public class TotalDto {
  private float churchFirstLeaderPercentage;
  private float churchSecondLeaderPercentage;
  private float mainChurchPercentage;
  private float mainLeaderPercentage;
  private float ministryPercentage;
  private float total;

  public TotalDto(
      float churchFirstLeaderPercentage,
      float churchSecondLeaderPercentage,
      float mainChurchPercentage,
      float mainLeaderPercentage,
      float ministryPercentage,
      float total) {
    this.churchFirstLeaderPercentage = churchFirstLeaderPercentage;
    this.churchSecondLeaderPercentage = churchSecondLeaderPercentage;
    this.mainChurchPercentage = mainChurchPercentage;
    this.mainLeaderPercentage = mainLeaderPercentage;
    this.ministryPercentage = ministryPercentage;
    this.total = total;
  }

  public float getChurchFirstLeaderPercentage() {
    return churchFirstLeaderPercentage;
  }

  public void setChurchFirstLeaderPercentage(float churchFirstLeaderPercentage) {
    this.churchFirstLeaderPercentage = churchFirstLeaderPercentage;
  }

  public float getChurchSecondLeaderPercentage() {
    return churchSecondLeaderPercentage;
  }

  public void setChurchSecondLeaderPercentage(float churchSecondLeaderPercentageTotal) {
    this.churchSecondLeaderPercentage = churchSecondLeaderPercentageTotal;
  }

  public float getMainChurchPercentage() {
    return mainChurchPercentage;
  }

  public void setMainChurchPercentage(float mainChurchPercentageTotal) {
    this.mainChurchPercentage = mainChurchPercentageTotal;
  }

  public float getMainLeaderPercentage() {
    return mainLeaderPercentage;
  }

  public void setMainLeaderPercentage(float mainLeaderPercentageTotal) {
    this.mainLeaderPercentage = mainLeaderPercentageTotal;
  }

  public float getMinistryPercentage() {
    return ministryPercentage;
  }

  public void setMinistryPercentage(float ministryPercentageTotal) {
    this.ministryPercentage = ministryPercentageTotal;
  }

  public float getTotal() {
    return total;
  }

  public void setTotal(float total) {
    this.total = total;
  }

}
