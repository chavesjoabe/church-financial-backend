package com.treasury.treasury.tax.dto;

public class CreateTaxDto {
  private Double leaderPercentage;
  private Double mainChurchPercentage;
  private Double ministryPercentage;
  private Double mainLeaderPercentage;

  public CreateTaxDto() {
  }

  public CreateTaxDto(

      Double leaderPercentage,
      Double mainChurchPercentage,
      Double ministryPercentage,
      Double mainLeaderPercentage) {
    this.leaderPercentage = leaderPercentage;
    this.mainChurchPercentage = mainChurchPercentage;
    this.ministryPercentage = ministryPercentage;
    this.mainLeaderPercentage = mainLeaderPercentage;
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

}
